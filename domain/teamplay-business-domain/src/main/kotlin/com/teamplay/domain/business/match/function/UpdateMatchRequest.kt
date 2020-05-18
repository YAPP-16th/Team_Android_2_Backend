package com.teamplay.domain.business.match.function

import com.teamplay.core.function.Function
import com.teamplay.domain.database.jpa.match.repository.MatchRepository
import com.teamplay.domain.database.match.entity.Match
import com.teamplay.domain.database.match.entity.MatchRequest
import com.teamplay.domain.database.match.entity.MatchRequestStatus
import com.teamplay.domain.database.match.entity.MatchStatus
import org.springframework.transaction.annotation.Transactional

open class UpdateMatchRequest(
    private val matchRepository: MatchRepository
): Function<MatchRequest, Match> {
    @Transactional
    override fun apply(matchRequest: MatchRequest): Match {
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

            match
        }
    }

}
