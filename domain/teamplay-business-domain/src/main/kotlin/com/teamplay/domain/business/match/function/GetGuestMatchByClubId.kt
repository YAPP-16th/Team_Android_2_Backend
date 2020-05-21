package com.teamplay.domain.business.match.function

import com.teamplay.core.function.Function
import com.teamplay.domain.database.jpa.match.repository.MatchRequestRepository
import com.teamplay.domain.database.match.entity.MatchRequest

class GetGuestMatchByClubId(
    private val matchRequestRepository: MatchRequestRepository
): Function<Long, MutableList<MatchRequest>> {
    override fun apply(id: Long): MutableList<MatchRequest> {
        return matchRequestRepository.findAllGuestMatch(id)
    }
}
