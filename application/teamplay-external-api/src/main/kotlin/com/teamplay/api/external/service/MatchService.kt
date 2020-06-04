package com.teamplay.api.com.teamplay.api.external.service

import com.teamplay.api.com.teamplay.api.external.response.MatchResponse.*
import com.teamplay.api.com.teamplay.api.external.request.MatchRequest.*
import com.teamplay.core.function.date.DateUtil
import com.teamplay.domain.business.club.dto.CheckIsClubAdminDto
import com.teamplay.domain.business.club.function.FindClubById
import com.teamplay.domain.business.club.validator.CheckExistClub
import com.teamplay.domain.business.club.validator.CheckIsClubAdmin
import com.teamplay.domain.business.match.dto.MatchDTO.*
import com.teamplay.domain.business.match.dto.MatchValidatorDTO.*
import com.teamplay.domain.business.match.function.*
import com.teamplay.domain.business.match.validator.*
import com.teamplay.domain.business.match.dto.type.MatchEnum.*
import com.teamplay.domain.database.jpa.club.repository.ClubAdminRepository
import com.teamplay.domain.database.jpa.club.repository.ClubRepository
import com.teamplay.domain.database.jpa.match.repository.MatchRepository
import com.teamplay.domain.database.jpa.match.repository.MatchRequestRepository
import com.teamplay.domain.database.jpa.match.repository.spec.MatchSpecs
import com.teamplay.domain.database.match.entity.*
import com.teamplay.domain.database.user.entity.User
import mu.KLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
import java.util.*
import javax.persistence.EntityManager

@Service
class MatchService (
        matchRepository: MatchRepository,
        matchRequestRepository: MatchRequestRepository,
        clubRepository: ClubRepository,
        clubAdminRepository: ClubAdminRepository,
        private val entityManager: EntityManager
) {
    private val matchFunction = MatchFunction(matchRepository, matchRequestRepository)
    private val matchValidation = MatchValidation(matchRepository, matchRequestRepository)

    private val findClubById = FindClubById(clubRepository)

    private val checkExistClub = CheckExistClub(clubRepository)
    private val checkIsClubAdmin = CheckIsClubAdmin(clubAdminRepository)

    private val dateUtil = DateUtil()

    fun getMatchesList(specs: MatchSpecs): MatchListResponse {
        matchValidation.checkValidMatchSpec(specs)
        return matchFunction.findAllMatchByMatchSpec(specs).run {
            val matchListsInfo = this.map{
                MatchListInfoDTO(it)
            }.content

            MatchListResponse(
                    totalPage = this.totalPages,
                    currentPage = this.number,
                    matchList = matchListsInfo,
                    filterTitle = createMatchListFilterTitle(
                            specs.startTimeFrom?.let{dateUtil.convertStringToDate(it)},
                            specs.startTimeTo?.let{dateUtil.convertStringToDate(it)},
                            specs.location,
                            specs.matchStyle
                    )
            )
        }
    }

    fun getMatchInfo(id: Long): MatchDetailResponse {
        matchValidation.checkExistMatch(id)

        return MatchDetailResponse(matchFunction.getMatchById(id))
    }

    fun getMatchSchedule(clubId: Long): MatchScheduleResponse {
        checkExistClub.verify(clubId)

        return MatchScheduleResponse(
                (createMatchSchedule(clubId) + createHostMatchSchedule(clubId) + createGuestMatchSchedule(clubId)).toMutableList()
        )
    }

    fun createMatch(createMatch: CreateMatch, user: User): Match {
        checkIsClubAdmin.verify(CheckIsClubAdminDto(
                userId = user.id!!,
                clubId = createMatch.requesterClubId
        ))

        val match = Match(
                host = findClubById(createMatch.requesterClubId),
                title = createMatch.createMatchDTO.title,
                location = createMatch.createMatchDTO.location,
                startTime = createMatch.createMatchDTO.startDate,
                endTime = createMatch.createMatchDTO.endDate,
                introduce = createMatch.createMatchDTO.introduce,
                matchStyle = createMatch.createMatchDTO.matchStyle
        )

        return matchFunction.saveMatch(match)
    }

    fun saveMatch(match: Match): Match {
        return matchFunction.saveMatch(match)
    }

    @Transactional
    fun saveMatchRequest(matchId: Long, createMatchRequest: CreateMatchRequest, user: User): MatchRequest {
        checkIsClubAdmin.verify(CheckIsClubAdminDto(
                userId = user.id!!,
                clubId = createMatchRequest.requesterClubId
        ))
        matchValidation.checkExistMatch(matchId)
        matchValidation.checkIsWaitingMatchById(matchId)
        matchValidation.checkAlreadyRequestMatch(CheckAlreadyRequestMatchDTO(
                matchId = matchId,
                requesterClubId = user.id!!
        ))

        return entityManager.merge(MatchRequest(
                requester = findClubById(createMatchRequest.requesterClubId),
                contact = createMatchRequest.contact
        ).apply{ this.match = matchFunction.getMatchById(matchId) })
    }

    fun responseMatchRequest(matchRequestId: Long, matchRequestStatus: MatchRequestStatus): Match {
        matchValidation.checkExistMatchRequest(matchRequestId)
        matchValidation.checkIsWaitingMatchById(
                matchFunction.getMatchIdByMatchRequestId(matchRequestId)
        )
        matchValidation.checkIsWaitingMatchRequestById(matchRequestId)

        val match = matchFunction.updateMatchRequest(UpdateMatchRequestDTO(matchRequestId, matchRequestStatus))
        return saveMatch(match)
    }

    fun enterMatchResult(matchId: Long, enterMatchResultRequest: EnterMatchResultRequest, user: User): Match {
        checkIsClubAdmin.verify(CheckIsClubAdminDto(
                userId = user.id!!,
                clubId = enterMatchResultRequest.requesterClubId
        ))
        matchValidation.checkExistMatch(matchId)
        matchValidation.checkIsCloseMatchById(matchId)

        val match = matchFunction.getMatchById(matchId)
        val matchDetailResult = enterMatchResultRequest.detailResult?.map {
            MatchDetailResult(
                    hostScore = it.hostScore,
                    guestScore = it.guestScore,
                    resultType = it.matchResultType
            )
        }
        val matchIndividualResult = enterMatchResultRequest.individualResult?.map {
            MatchIndividualResult(
                    score = it.score,
                    receiver = it.receiver,
                    resultType = it.matchResultType
            )
        }

        match.hostScore = enterMatchResultRequest.hostScore
        match.guestScore = enterMatchResultRequest.guestScore
        match.matchStatus = MatchStatus.END
        match.matchResultReview = enterMatchResultRequest.matchReview
        match.winner = if(enterMatchResultRequest.hostScore > enterMatchResultRequest.guestScore) match.host else match.guest
        matchDetailResult?.let { match.matchDetailResult?.addAll(it) }
        matchIndividualResult?.let { match.matchIndividualResult?.addAll(it) }

        return saveMatch(match)
    }

    fun getMatchSummaryResult(matchId: Long): MutableList<MatchSummaryResultDTO> {
        matchValidation.checkExistMatch(matchId)
        matchValidation.checkIsEndMatchById(matchId)

        val match = matchFunction.getMatchById(matchId)
        val matchHostDetailResultDto = MatchSummaryResultDTO(
                matchClubType = MatchClubType.HOST,
                totalScore = match.hostScore ?: 0,
                recentlyRecord = matchFunction.getRecentlyRecordByClubId(match.host.id!!),
                isVictory = match.hostScore ?: 0 > match.guestScore ?: 0
        )
        val matchGuestDetailResultDto = MatchSummaryResultDTO(
                matchClubType = MatchClubType.GUEST,
                totalScore = match.guestScore ?: 0,
                recentlyRecord = matchFunction.getRecentlyRecordByClubId(match.guest!!.id!!),
                isVictory = match.guestScore ?: 0 > match.hostScore ?: 0
        )

        return mutableListOf(matchHostDetailResultDto, matchGuestDetailResultDto)
    }

    fun getMatchDetailResult(matchId: Long): MatchDetailResultDTO {
        matchValidation.checkExistMatch(matchId)
        matchValidation.checkIsEndMatchById(matchId)

        val match = matchFunction.getMatchById(matchId)
        return MatchDetailResultDTO(
                hostName = match.host.name,
                guestName = match.guest?.name ?: "",
                matchDetailResultScore = match.matchDetailResult?.map {
                    MatchDetailResultScoreDTO(
                            matchResultType = it.resultType,
                            hostScore = it.hostScore,
                            guestScore = it.guestScore
                    )
                }?.toMutableList() ?: mutableListOf()
        )
    }

    fun getMatchIndividualResult(matchId: Long): MutableList<MatchIndividualResultDTO> {
        matchValidation.checkExistMatch(matchId)
        matchValidation.checkIsEndMatchById(matchId)

        return matchFunction.getMatchById(matchId).matchIndividualResult?.map {
            MatchIndividualResultDTO(
                    matchResultType = it.resultType,
                    score = it.score,
                    recevier = it.receiver
            )
        }?.toMutableList() ?: mutableListOf()
    }

    private fun createMatchListFilterTitle(startTimeFrom: Date?, startTimeTo: Date?, location: String?, matchStyle: MatchStyle?): String {
        val dateFormat = SimpleDateFormat(MMDD_FORMAT)

        return mutableListOf(
                listOf(startTimeFrom, startTimeTo).any{ it != null }.run{
                    if (this) (startTimeFrom?.let{ dateFormat.format(it) } ?: "") + " - " + (startTimeTo?.let{ dateFormat.format(it) } ?: "")
                    else null
                },
                location,
                matchStyle?.toString()
        ).filterNotNull().joinToString(separator = " | ")
    }

    private fun createMatchSchedule(clubId: Long): MutableList<MatchScheduleListDTO> {
        val dateFormat = SimpleDateFormat(MMDD_FORMAT)
        val timeFormat = SimpleDateFormat(HHMM_FORMAT)
        val now = LocalDate.now()
        val startThisWeek = dateUtil.getStartDayByLocalDate(now.with(DayOfWeek.MONDAY))
        val endThisWeek = dateUtil.getEndDayByLocalDate(now.with(DayOfWeek.SUNDAY))
        val nextStartWeek = dateUtil.getStartDayByLocalDate(now.with(TemporalAdjusters.next(DayOfWeek.MONDAY)))
        val nextEndWeek = if (now.dayOfWeek.value == 7) {
            dateUtil.getEndDayByLocalDate(now.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)))
        } else {
            dateUtil.getEndDayByLocalDate(now.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)).with(TemporalAdjusters.next(DayOfWeek.SUNDAY)))
        }
        val oneWeekLaterMatchScheduleInfo : MutableList<MatchScheduleInfoDTO> = mutableListOf()
        val twoWeekLaterMatchScheduleInfo : MutableList<MatchScheduleInfoDTO> = mutableListOf()

        matchFunction.getMatchScheduleByClubId(
                MatchScheduleRequestDTO(
                        clubId,
                        startThisWeek,
                        endThisWeek
                )
        ).forEach{
            oneWeekLaterMatchScheduleInfo.add(
                    MatchScheduleInfoDTO(
                            title = "${it.host.name} vs ${it.guest?.name ?: ""}",
                            description = "${it.matchStyle} | ${it.location}",
                            matchDate = dateFormat.format(it.startTime),
                            matchTime = "${timeFormat.format(it.startTime)} - ${timeFormat.format(it.endTime)}"
                    )
            )
        }

        matchFunction.getMatchScheduleByClubId(
                MatchScheduleRequestDTO(
                        clubId,
                        nextStartWeek,
                        nextEndWeek
                )
        ).forEach{
            twoWeekLaterMatchScheduleInfo.add(
                    MatchScheduleInfoDTO(
                            title = "${it.host.name} vs ${it.guest?.name ?: ""}",
                            description = "${it.matchStyle} | ${it.location}",
                            matchDate = dateFormat.format(it.startTime),
                            matchTime = "${timeFormat.format(it.startTime)} - ${timeFormat.format(it.endTime)}"
                    )
            )
        }

        return mutableListOf(
                MatchScheduleListDTO(
                        scheduleTitle = "이번주",
                        matchScheduleInfo = oneWeekLaterMatchScheduleInfo,
                        matchScheduleType = MatchScheduleType.THIS_SCHEDULE
                ),
                MatchScheduleListDTO(
                        scheduleTitle = "다음주",
                        matchScheduleInfo = twoWeekLaterMatchScheduleInfo,
                        matchScheduleType = MatchScheduleType.NEXT_SCHEDULE
                )
        )
    }

    private fun createHostMatchSchedule(clubId: Long): MutableList<MatchScheduleListDTO> {
        val hostMatchRequestList = matchFunction.getHostMatchByClubId(clubId).run{
            this.map {
                MatchScheduleInfoDTO(
                        title = "${it.requester.name}이(가) 매치를 신청했습니다.",
                        description = it.contact,
                        matchRequestId = it.id
                )
            }.toMutableList()
        }

        return mutableListOf(
                MatchScheduleListDTO(
                        scheduleTitle = "호스트",
                        matchScheduleInfo = hostMatchRequestList,
                        matchScheduleType = MatchScheduleType.HOST
                )
        )
    }

    private fun createGuestMatchSchedule(clubId: Long): MutableList<MatchScheduleListDTO> {
        val guestMatchRequestList = matchFunction.getGuestMatchByClubId(clubId).run{
            this.map {
                MatchScheduleInfoDTO(
                        title = "${it.match.host.name}에 매치를 신청했습니다.",
                        description = "${it.match.matchStyle} | ${it.match.location}",
                        requestStatus = it.matchRequestStatus,
                        matchRequestId = it.id
                )
            }.toMutableList()
        }

        return mutableListOf(
                MatchScheduleListDTO(
                        scheduleTitle = "게스트",
                        matchScheduleInfo = guestMatchRequestList,
                        matchScheduleType = MatchScheduleType.GUEST
                )
        )
    }

    companion object: KLogging() {
        private const val MMDD_FORMAT = "MM월 dd일"
        private const val HHMM_FORMAT = "hh:mm"
    }
}
