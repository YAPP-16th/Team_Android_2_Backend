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
import mu.KLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.temporal.TemporalAdjusters
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
                MatchInfo(
                        title = it.title,
                        hostName = it.home.name,
                        matchStartTime = it.startTime,
                        matchEndTime = it.endTime,
                        location = it.location,
                        matchStyle =  it.matchStyle,
                        introduce = it.introduce
                )
            }.content

            MatchListResponse(
                    totalPage = this.totalPages,
                    currentPage = this.number,
                    matchList = matchList
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
            MatchInfo(
                    title = this.title,
                    hostName = this.home.name,
                    matchStartTime = this.startTime,
                    matchEndTime = this.endTime,
                    location = this.location,
                    matchStyle =  this.matchStyle,
                    introduce = this.introduce
            )
        }
    }

    fun getMatchSchedule(clubId: Long): MatchScheduleResponse {
        checkExistClub.verify(clubId)

        val now = LocalDate.now()
        val startThisWeek = dateUtil.getStartDayByLocalDate(now.with(DayOfWeek.MONDAY))
        val endThisWeek = dateUtil.getEndDayByLocalDate(now.with(DayOfWeek.SUNDAY))
        val nextStartWeek = dateUtil.getStartDayByLocalDate(now.with(TemporalAdjusters.next(DayOfWeek.MONDAY)))
        val nextEndWeek = dateUtil.getEndDayByLocalDate(now.with(TemporalAdjusters.next(DayOfWeek.SUNDAY)))
        val matchSchedule = getMatchScheduleByClubId(
                MatchScheduleRequest(
                        clubId,
                        startThisWeek,
                        nextEndWeek
                )
        ).run{
            val oneWeekLaterMatchScheduleInfo : MutableList<MatchScheduleInfo> = mutableListOf()
            val twoWeekLaterMatchScheduleInfo : MutableList<MatchScheduleInfo> = mutableListOf()
            this.map{
                if (dateUtil.isBetweenDate(startThisWeek, endThisWeek, it.startTime)) {
                    oneWeekLaterMatchScheduleInfo.add(
                            MatchScheduleInfo(
                                    homeName = it.home.name,
                                    awayName = it.away?.name ?: "",
                                    matchStyle = it.matchStyle,
                                    location = it.location,
                                    matchStartTime = it.startTime,
                                    matchEndTime = it.endTime
                            )
                    )
                } else if (dateUtil.isBetweenDate(nextStartWeek, nextEndWeek, it.startTime)) {
                    twoWeekLaterMatchScheduleInfo.add(
                            MatchScheduleInfo(
                                    homeName = it.home.name,
                                    awayName = it.away?.name ?: "",
                                    matchStyle = it.matchStyle,
                                    location = it.location,
                                    matchStartTime = it.startTime,
                                    matchEndTime = it.endTime
                            )
                    )
                } else {
                    logger.error("[MATCH SERVICE ERROR] Match schedule response is invalid. matchId: {}, matchTitle: {}",
                            it.id,
                            it.title
                    )
                }
            }

            mutableListOf(oneWeekLaterMatchScheduleInfo, twoWeekLaterMatchScheduleInfo)
        }
        val hostMatchRequest = getGuestMatchByClubId(clubId).run{
            this.map {
                MatchHostScheduleInfo(
                    hostName = it.match.home.name,
                    matchStyle = it.match.matchStyle,
                    location = it.match.location
                )
            }.toMutableList()
        }
        val guestMatchRequest = getHostMatchByClubId(clubId).run{
            this.map {
                MatchGuestScheduleInfo(
                    requesterName = it.requester.name,
                    matchStyle = it.match.matchStyle,
                    location = it.match.location,
                    requestStatus = it.matchRequestStatus
                )
            }.toMutableList()
        }

        return MatchScheduleResponse(
                matchSchedule = matchSchedule,
                hostMatchRequest = hostMatchRequest,
                guestMatchRequest = guestMatchRequest
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

    companion object: KLogging()
}
