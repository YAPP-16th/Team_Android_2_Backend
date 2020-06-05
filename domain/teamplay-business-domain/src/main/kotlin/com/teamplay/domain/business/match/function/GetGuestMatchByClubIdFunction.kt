package com.teamplay.domain.business.match.function

import com.teamplay.core.function.Function
import com.teamplay.domain.database.jpa.match.repository.MatchRequestRepository
import com.teamplay.domain.database.match.entity.MatchRequest

class GetGuestMatchByClubIdFunction(
    private val repository: MatchRequestRepository
): Function<Long, MutableList<MatchRequest>> {
    override fun apply(clubId: Long): MutableList<MatchRequest> {
        return repository.findAllGuestMatch(clubId)
    }
}
