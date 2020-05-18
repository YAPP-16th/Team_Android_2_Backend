package com.teamplay.api.com.teamplay.api.external.service

import com.teamplay.domain.business.match.dto.MatchScheduleResponse
import com.teamplay.domain.business.match.function.*
import com.teamplay.domain.business.match.validator.CheckExistMatchById
import com.teamplay.domain.business.match.validator.CheckExistMatchRequest
import com.teamplay.domain.business.match.validator.CheckValidMatchSpec
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
        private val entityManager: EntityManager
) {
    private val findAllMatchByMatchSpec = FindAllMatchByMatchSpec(matchRepository)
    private val getMatchById = GetMatchById(matchRepository)
    private val getMatchScheduleByClubId = GetMatchScheduleByClubId(matchRepository, matchRequestRepository)
    private val saveMatch = SaveMatch(matchRepository)
    private val updateMatchRequest = UpdateMatchRequest(matchRepository)

    private val checkValidMatchSpec = CheckValidMatchSpec()
    private val checkExistMatchById = CheckExistMatchById(matchRepository)
    private val checkExistMatchRequest = CheckExistMatchRequest(matchRequestRepository)

    fun find(specs: MatchSpecs): Page<Match> {
        checkValidMatchSpec.verify(specs)
        return findAllMatchByMatchSpec(specs)
    }

    fun get(id: Long): Match {
        checkExistMatchById.verify(id)
        return getMatchById(id)
    }

    fun getMatchSchedule(clubId: Long): MatchScheduleResponse {
        // club 머지 후 club id verify 추가
        return getMatchScheduleByClubId(clubId)
    }

    fun save(match: Match): Match {
        return saveMatch(match)
    }

    @Transactional
    fun saveMatchRequest(matchId: Long, matchRequest: MatchRequest): MatchRequest {
        checkExistMatchById.verify(matchId)
        matchRequest.match = get(matchId)
        return entityManager.merge(matchRequest)
    }

    fun responseMatchRequest(matchRequest: MatchRequest): Match {
        checkExistMatchRequest(matchRequest.id)
        val match = updateMatchRequest(matchRequest)
        return save(match)
    }
}
