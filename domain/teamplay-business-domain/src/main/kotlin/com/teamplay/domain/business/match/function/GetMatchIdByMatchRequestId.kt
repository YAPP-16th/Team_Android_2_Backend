package com.teamplay.domain.business.match.function

import com.teamplay.core.function.Function
import com.teamplay.domain.database.jpa.match.repository.MatchRequestRepository

class GetMatchIdByMatchRequestId(
    private val matchRequestRepository: MatchRequestRepository
): Function<Long, Long> {
    override fun apply(id: Long): Long {
        return matchRequestRepository.getMatchIdByMatchRequestId(id)
    }
}
