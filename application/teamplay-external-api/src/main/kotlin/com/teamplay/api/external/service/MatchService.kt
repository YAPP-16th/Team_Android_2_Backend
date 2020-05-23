package com.teamplay.api.com.teamplay.api.external.service

import com.teamplay.api.com.teamplay.api.external.response.MatchListResponse
import com.teamplay.api.com.teamplay.api.external.response.MatchScheduleResponse
import com.teamplay.core.function.date.DateUtil
import com.teamplay.domain.business.club.validator.CheckExistClub
import com.teamplay.domain.business.match.dto.*
import com.teamplay.domain.business.match.function.*
import com.teamplay.domain.business.match.validator.CheckExistMatchById
import com.teamplay.domain.business.match.validator.CheckExistMatchRequest
import com.teamplay.domain.business.match.validator.CheckIsWaitingMatchById
import com.teamplay.domain.business.match.validator.CheckValidMatchSpec
import com.teamplay.domain.database.jpa.club.repository.ClubRepository
import com.teamplay.domain.database.jpa.match.repository.MatchRepository
import com.teamplay.domain.database.jpa.match.repository.MatchRequestRepository
import com.teamplay.domain.database.jpa.match.repository.spec.MatchSpecs
import com.teamplay.domain.database.match.entity.Match
import com.teamplay.domain.database.match.entity.MatchRequest
import com.teamplay.domain.database.match.entity.MatchStyle
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
        private val entityManager: EntityManager
) {
    private val findAllMatchByMatchSpec = FindAllMatchByMatchSpec(matchRepository)
    private val getMatchByIdFunction = GetMatchById(matchRepository)
    private val getMatchScheduleByClubId = GetMatchScheduleByClubId(matchRepository)
    private val getGuestMatchByClubId = GetGuestMatchByClubId(matchRequestRepository)
    private val getHostMatchByClubId = GetHostMatchByClubId(matchRequestRepository)
    private val saveMatchFunction = SaveMatch(matchRepository)
    private val updateMatchRequest = UpdateMatchRequest(matchRepository)

    private val checkValidMatchSpec = CheckValidMatchSpec()
    private val checkExistMatchById = CheckExistMatchById(matchRepository)
    private val checkIsWaitingMatchById = CheckIsWaitingMatchById(matchRepository)
    private val checkExistMatchRequest = CheckExistMatchRequest(matchRequestRepository)
    private val checkExistClub = CheckExistClub(clubRepository)

    private val dateUtil = DateUtil()

    fun findMatches(specs: MatchSpecs): MatchListResponse {
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

    fun getMatch(id: Long): Match {
        checkExistMatchById.verify(id)
        return getMatchByIdFunction(id)
    }

    fun getMatchInfo(id: Long): MatchInfo {
        checkExistMatchById.verify(id)

        return getMatchByIdFunction(id).run {
            MatchInfo(this)
        }
    }

    fun getMatchSchedule(clubId: Long): MatchScheduleResponse {
        checkExistClub.verify(clubId)

        return MatchScheduleResponse(
                (createMatchSchedule(clubId) + createHostMatchSchedule(clubId) + createGuestMatchSchedule(clubId)).toMutableList()
        )
    }

    fun saveMatch(match: Match): Match {
        return saveMatchFunction(match)
    }

    @Transactional
    fun saveMatchRequest(matchId: Long, matchRequest: MatchRequest): MatchRequest {
        checkExistMatchById.verify(matchId)
        checkIsWaitingMatchById.verify(matchId)

        matchRequest.match = getMatch(matchId)
        return entityManager.merge(matchRequest)
    }

    fun responseMatchRequest(matchRequest: MatchRequest): Match {
        checkExistMatchRequest(matchRequest.id)
        val match = updateMatchRequest(matchRequest)
        return saveMatch(match)
    }

    private fun createMatchListFilterTitle(startTimeFrom: Date?, startTimeTo: Date?, location: String?, matchStyle: MatchStyle?): String {
        val dateFormat = SimpleDateFormat(MMDD_FORMAT)

        return mutableListOf(
                listOf(startTimeFrom, startTimeTo).any{ it != null }.run{
                    if (this) (startTimeFrom?.let{ dateFormat.format(it) }) ?: "" + " - " + (startTimeTo?.let{ dateFormat.format(it) } ?: "")
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
        val nextEndWeek = dateUtil.getEndDayByLocalDate(now.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)))
        val oneWeekLaterMatchScheduleInfo : MutableList<MatchScheduleInfo> = mutableListOf()
        val twoWeekLaterMatchScheduleInfo : MutableList<MatchScheduleInfo> = mutableListOf()

        getMatchScheduleByClubId(
                MatchScheduleRequest(
                        clubId,
                        startThisWeek,
                        nextEndWeek
                )
        ).map{
            if (dateUtil.isBetweenDate(startThisWeek, endThisWeek, it.startTime)) {
                oneWeekLaterMatchScheduleInfo.add(
                        MatchScheduleInfo(
                                title = "${it.home.name} vs ${it.away?.name ?: ""}",
                                description = "${it.matchStyle} | ${it.location}",
                                matchDate = dateFormat.format(it.startTime),
                                matchTime = "${timeFormat.format(it.startTime)} - ${timeFormat.format(it.endTime)}"
                        )
                )
            } else if (dateUtil.isBetweenDate(nextStartWeek, nextEndWeek, it.startTime)) {
                twoWeekLaterMatchScheduleInfo.add(
                        MatchScheduleInfo(
                                title = "${it.home.name} vs ${it.away?.name ?: ""}",
                                description = "${it.matchStyle} | ${it.location}",
                                matchDate = dateFormat.format(it.startTime),
                                matchTime = "${timeFormat.format(it.startTime)} - ${timeFormat.format(it.endTime)}"
                        )
                )
            } else {
                logger.error("[MATCH SERVICE ERROR] Match schedule response is invalid. matchId: {}, matchTitle: {}",
                        it.id,
                        it.title
                )
            }
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
                        title = "${it.requester.name}에 매치를 신청했습니다.",
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
