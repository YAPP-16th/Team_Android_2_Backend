package com.teamplay.domain.business.match.function

import com.teamplay.core.function.Function
import com.teamplay.domain.database.jpa.match.repository.MatchRequestRepository

class GetMatchIdByMatchRequestIdFunction(
    private val repository: MatchRequestRepository
): Function<Long, Long> {
    override fun apply(matchRequestId: Long): Long {
        return repository.getMatchIdByMatchRequestId(matchRequestId)
    }
}
