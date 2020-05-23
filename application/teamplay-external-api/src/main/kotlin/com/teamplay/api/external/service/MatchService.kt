package com.teamplay.api.com.teamplay.api.external.service

import com.teamplay.api.com.teamplay.api.external.response.MatchScheduleResponse
import com.teamplay.domain.business.club.validator.CheckExistClub
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
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
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

    fun findMatches(specs: MatchSpecs): Page<Match> {
        checkValidMatchSpec.verify(specs)
        return findAllMatchByMatchSpec(specs)
    }

    fun getMatch(id: Long): Match {
        checkExistMatchById.verify(id)
        return getMatchByIdFunction(id)
    }

    fun getMatchSchedule(clubId: Long): MatchScheduleResponse {
        checkExistClub.verify(clubId)

        return MatchScheduleResponse(
                matchSchedule = getMatchScheduleByClubId(clubId),
                hostMatchRequest = getGuestMatchByClubId(clubId),
                guestMatchRequest = getHostMatchByClubId(clubId)
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
}
