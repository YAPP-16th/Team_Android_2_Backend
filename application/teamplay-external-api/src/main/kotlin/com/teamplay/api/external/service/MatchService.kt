package com.teamplay.api.com.teamplay.api.external.service

import com.teamplay.api.com.teamplay.api.external.request.CreateMatch
import com.teamplay.api.com.teamplay.api.external.request.CreateMatchRequest
import com.teamplay.api.com.teamplay.api.external.request.EnterMatchResultRequest
import com.teamplay.api.com.teamplay.api.external.response.MatchListResponse
import com.teamplay.api.com.teamplay.api.external.response.MatchScheduleResponse
import com.teamplay.core.function.date.DateUtil
import com.teamplay.domain.business.club.dto.CheckIsClubAdminDto
import com.teamplay.domain.business.club.function.FindClubById
import com.teamplay.domain.business.club.validator.CheckExistClub
import com.teamplay.domain.business.club.validator.CheckIsClubAdmin
import com.teamplay.domain.business.match.dto.*
import com.teamplay.domain.business.match.function.*
import com.teamplay.domain.business.match.validator.*
import com.teamplay.domain.database.jpa.club.repository.ClubAdminRepository
import com.teamplay.domain.database.jpa.club.repository.ClubRepository
import com.teamplay.domain.database.jpa.match.repository.MatchRepository
import com.teamplay.domain.database.jpa.match.repository.MatchRequestRepository
import com.teamplay.domain.database.jpa.match.repository.spec.MatchSpecs
import com.teamplay.domain.database.match.entity.*
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
    private val findAllMatchByMatchSpec = FindAllMatchByMatchSpec(matchRepository)
    private val getMatchByIdFunction = GetMatchById(matchRepository)
    private val getMatchScheduleByClubId = GetMatchScheduleByClubId(matchRepository)
    private val saveMatchFunction = SaveMatch(matchRepository)
    private val updateMatchRequest = UpdateMatchRequest(matchRepository)
    private val getRecentlyRecordById = GetRecentlyRecordById(matchRepository)
    private val getGuestMatchByClubId = GetGuestMatchByClubId(matchRequestRepository)
    private val getHostMatchByClubId = GetHostMatchByClubId(matchRequestRepository)
    private val getMatchIdByMatchRequestId = GetMatchIdByMatchRequestId(matchRequestRepository)
    private val findClubById = FindClubById(clubRepository)

    private val checkExistMatchById = CheckExistMatchById(matchRepository)
    private val checkIsWaitingMatchById = CheckIsWaitingMatchById(matchRepository)
    private val checkIsCloseMatchById = CheckIsCloseMatchById(matchRepository)
    private val checkIsEndMatchById = CheckIsEndMatchById(matchRepository)
    private val checkExistMatchRequest = CheckExistMatchRequest(matchRequestRepository)
    private val checkAlreadyRequestMatch = CheckAlreadyRequestMatch(matchRequestRepository)
    private val checkIsWaitingMatchRequestById = CheckIsWaitingMatchRequestById(matchRequestRepository)
    private val checkExistClub = CheckExistClub(clubRepository)
    private val checkIsClubAdmin = CheckIsClubAdmin(clubAdminRepository)
    private val checkValidMatchSpec = CheckValidMatchSpec()

    private val dateUtil = DateUtil()

    fun getMatchesList(specs: MatchSpecs): MatchListResponse {
        checkValidMatchSpec.verify(specs)
        return findAllMatchByMatchSpec(specs).run {
            val matchList = this.map{
                MatchList(it)
            }.content

            MatchListResponse(
                    totalPage = this.totalPages,
                    currentPage = this.number,
                    matchList = matchList,
                    filterTitle = createMatchListFilterTitle(specs.startTimeFrom, specs.startTimeTo, specs.location, specs.matchStyle)
            )
        }
    }

    fun getMatchInfo(id: Long): MatchInfo {
        checkExistMatchById.verify(id)
        return MatchInfo(getMatchByIdFunction(id))
    }

    fun getMatchSchedule(clubId: Long): MatchScheduleResponse {
        checkExistClub.verify(clubId)

        return MatchScheduleResponse(
                (createMatchSchedule(clubId) + createHostMatchSchedule(clubId) + createGuestMatchSchedule(clubId)).toMutableList()
        )
    }

    fun createMatch(createMatch: CreateMatch): Match {
        checkIsClubAdmin.verify(CheckIsClubAdminDto(
                userId = createMatch.requesterUserId,
                clubId = createMatch.requesterClubId
        ))

        val match = Match(
                home = findClubById(createMatch.requesterClubId),
                title = createMatch.createMatchDto.title,
                location = createMatch.createMatchDto.location,
                startTime = createMatch.createMatchDto.startDate,
                endTime = createMatch.createMatchDto.endDate,
                introduce = createMatch.createMatchDto.introduce,
                matchStyle = createMatch.createMatchDto.matchStyle
        )

        return saveMatchFunction(match)
    }

    fun saveMatch(match: Match): Match {
        return saveMatchFunction(match)
    }

    @Transactional
    fun saveMatchRequest(matchId: Long, createMatchRequest: CreateMatchRequest): MatchRequest {
        checkIsClubAdmin.verify(CheckIsClubAdminDto(
                userId = createMatchRequest.requesterUserId,
                clubId = createMatchRequest.requesterClubId
        ))
        checkExistMatchById.verify(matchId)
        checkIsWaitingMatchById.verify(matchId)
        checkAlreadyRequestMatch.verify(CheckAlreadyRequestMatchDto(
                matchId = matchId,
                requesterClubId = createMatchRequest.requesterClubId
        ))

        return entityManager.merge(MatchRequest(
                requester = findClubById(createMatchRequest.requesterClubId),
                contact = createMatchRequest.contact
        ).apply{ this.match = getMatchByIdFunction(matchId) })
    }

    fun responseMatchRequest(matchRequestId: Long, matchRequestStatus: MatchRequestStatus): Match {
        checkExistMatchRequest.verify(matchRequestId)
        checkIsWaitingMatchById.verify(
                getMatchIdByMatchRequestId(matchRequestId)
        )
        checkIsWaitingMatchRequestById.verify(matchRequestId)

        val match = updateMatchRequest(UpdateMatchRequestDto(matchRequestId, matchRequestStatus))
        return saveMatch(match)
    }

    fun enterMatchResult(matchId: Long, enterMatchResultRequest: EnterMatchResultRequest): Match {
        checkIsClubAdmin.verify(CheckIsClubAdminDto(
                userId = enterMatchResultRequest.requesterUserId,
                clubId = enterMatchResultRequest.requesterClubId
        ))
        checkExistMatchById.verify(matchId)
        checkIsCloseMatchById.verify(matchId)

        val match = getMatchByIdFunction(matchId)
        val matchDetailResult = enterMatchResultRequest.detailResult?.map {
            MatchDetailResult(
                    homeScore = it.hostScore,
                    awayScore = it.guestScore,
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

        match.homeScore = enterMatchResultRequest.homeScore
        match.awayScore = enterMatchResultRequest.awayScore
        match.matchStatus = MatchStatus.END
        match.matchResultReview = enterMatchResultRequest.matchReview
        match.winner = if(enterMatchResultRequest.homeScore > enterMatchResultRequest.awayScore) match.home else match.away
        matchDetailResult?.let { match.matchDetailResult?.addAll(it) }
        matchIndividualResult?.let { match.matchIndividualResult?.addAll(it) }

        return saveMatch(match)
    }

    fun getMatchSummaryResult(matchId: Long): MutableList<MatchSummaryResult> {
        checkExistMatchById.verify(matchId)
        checkIsEndMatchById.verify(matchId)

        val match = getMatchByIdFunction(matchId)
        val matchHomeDetailResultDto = MatchSummaryResult(
                matchClubType = MatchClubType.HOME,
                totalScore = match.homeScore ?: 0,
                recentlyRecord = getRecentlyRecordById(match.home.id!!),
                isVictory = match.homeScore ?: 0 > match.awayScore ?: 0
        )
        val matchAwayDetailResultDto = MatchSummaryResult(
                matchClubType = MatchClubType.AWAY,
                totalScore = match.awayScore ?: 0,
                recentlyRecord = getRecentlyRecordById(match.away!!.id!!),
                isVictory = match.awayScore ?: 0 > match.homeScore ?: 0
        )

        return mutableListOf(matchHomeDetailResultDto, matchAwayDetailResultDto)
    }

    fun getMatchDetailResult(matchId: Long): MatchDetailResultDto {
        checkExistMatchById.verify(matchId)
        checkIsEndMatchById.verify(matchId)

        val match = getMatchByIdFunction(matchId)
        return MatchDetailResultDto(
                homeName = match.home.name,
                awayName = match.away?.name ?: "",
                matchDetailResultScore = match.matchDetailResult?.map {
                    MatchDetailResultScore(
                            matchResultType = it.resultType,
                            homeScore = it.homeScore,
                            awayScore = it.awayScore
                    )
                }?.toMutableList() ?: mutableListOf()
        )
    }

    fun getMatchIndividualResult(matchId: Long): MutableList<MatchIndividualResultDto> {
        checkExistMatchById.verify(matchId)
        checkIsEndMatchById.verify(matchId)

        return getMatchByIdFunction(matchId).matchIndividualResult?.map {
            MatchIndividualResultDto(
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

    private fun createMatchSchedule(clubId: Long): MutableList<MatchScheduleList> {
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
        val oneWeekLaterMatchScheduleInfo : MutableList<MatchScheduleInfo> = mutableListOf()
        val twoWeekLaterMatchScheduleInfo : MutableList<MatchScheduleInfo> = mutableListOf()

        getMatchScheduleByClubId(
                MatchScheduleRequest(
                        clubId,
                        startThisWeek,
                        endThisWeek
                )
        ).forEach{
            oneWeekLaterMatchScheduleInfo.add(
                    MatchScheduleInfo(
                            title = "${it.home.name} vs ${it.away?.name ?: ""}",
                            description = "${it.matchStyle} | ${it.location}",
                            matchDate = dateFormat.format(it.startTime),
                            matchTime = "${timeFormat.format(it.startTime)} - ${timeFormat.format(it.endTime)}"
                    )
            )
        }

        getMatchScheduleByClubId(
                MatchScheduleRequest(
                        clubId,
                        nextStartWeek,
                        nextEndWeek
                )
        ).forEach{
            twoWeekLaterMatchScheduleInfo.add(
                    MatchScheduleInfo(
                            title = "${it.home.name} vs ${it.away?.name ?: ""}",
                            description = "${it.matchStyle} | ${it.location}",
                            matchDate = dateFormat.format(it.startTime),
                            matchTime = "${timeFormat.format(it.startTime)} - ${timeFormat.format(it.endTime)}"
                    )
            )
        }

        return mutableListOf(
                MatchScheduleList(
                        scheduleTitle = "이번주",
                        matchScheduleInfo = oneWeekLaterMatchScheduleInfo,
                        matchScheduleType = MatchScheduleType.THIS_SCHEDULE
                ),
                MatchScheduleList(
                        scheduleTitle = "다음주",
                        matchScheduleInfo = twoWeekLaterMatchScheduleInfo,
                        matchScheduleType = MatchScheduleType.NEXT_SCHEDULE
                )
        )
    }

    private fun createHostMatchSchedule(clubId: Long): MutableList<MatchScheduleList> {
        val hostMatchRequestList = getHostMatchByClubId(clubId).run{
            this.map {
                MatchScheduleInfo(
                        title = "${it.requester.name}이(가) 매치를 신청했습니다.",
                        description = it.contact,
                        matchRequestId = it.id
                )
            }.toMutableList()
        }

        return mutableListOf(
                MatchScheduleList(
                        scheduleTitle = "호스트",
                        matchScheduleInfo = hostMatchRequestList,
                        matchScheduleType = MatchScheduleType.HOST
                )
        )
    }

    private fun createGuestMatchSchedule(clubId: Long): MutableList<MatchScheduleList> {
        val guestMatchRequestList = getGuestMatchByClubId(clubId).run{
            this.map {
                MatchScheduleInfo(
                        title = "${it.match.home.name}에 매치를 신청했습니다.",
                        description = "${it.match.matchStyle} | ${it.match.location}",
                        requestStatus = it.matchRequestStatus,
                        matchRequestId = it.id
                )
            }.toMutableList()
        }

        return mutableListOf(
                MatchScheduleList(
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
