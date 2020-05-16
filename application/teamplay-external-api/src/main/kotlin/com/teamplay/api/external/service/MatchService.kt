package com.teamplay.api.com.teamplay.api.external.service

import com.teamplay.api.com.teamplay.api.external.response.MatchScheduleResponse
import com.teamplay.domain.database.jpa.match.repository.MatchRepository
import com.teamplay.domain.database.jpa.match.repository.MatchRequestRepository
import com.teamplay.domain.database.jpa.match.repository.spec.MatchSpecs
import com.teamplay.domain.database.match.entity.Match
import com.teamplay.domain.database.match.entity.MatchRequest
import com.teamplay.domain.database.match.entity.MatchRequestStatus
import com.teamplay.domain.database.match.entity.MatchStatus
import org.springframework.data.domain.Page
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Service
class MatchService (
        private val matchRepository: MatchRepository,
        private val matchRequestRepository: MatchRequestRepository,
        private val entityManager: EntityManager
) {
    fun find(specs: MatchSpecs): Page<Match> {
        return matchRepository.findAll(specs.createSpecs(), specs.createPageRequest())
    }

    fun get(id: Long): Match {
        return matchRepository.getOne(id)
    }

    fun getMatchSchedule(clubId: Long): MatchScheduleResponse {
        val matchSchedule = matchRepository.findAllAcceptMatch(clubId)
        val hostMatchRequest = matchRequestRepository.findAllHostMatch(clubId)
        val guestMatchRequest = matchRequestRepository.findAllGuestMatch(clubId)

        return MatchScheduleResponse(
                matchSchedule, hostMatchRequest, guestMatchRequest
        )
    }

    @Transactional
    fun save(match: Match): Match {
        return matchRepository.save(match.prepareForSave())
    }

    @Transactional
    fun saveMatchRequest(matchId: Long, matchRequest: MatchRequest): MatchRequest {
        matchRequest.match = get(matchId)
        return entityManager.merge(matchRequest)
    }

    @Transactional
    fun responseMatchRequest(matchRequest: MatchRequest): Match {
        return matchRepository.getByMatchRequests(matchRequest).let { match ->
            if (matchRequest.matchRequestStatus == MatchRequestStatus.ACCEPT) {
                match.matchStatus = MatchStatus.CLOSE

                match.matchRequests?.map{
                    if (it.id != matchRequest.id) {
                        it.matchRequestStatus = MatchRequestStatus.REJECT
                    }
                }
            }
            match.matchRequests?.indexOf(matchRequest)?.let {
                match.matchRequests!!.set(it, matchRequest)
            }

            save(match)
        }
    }
}
