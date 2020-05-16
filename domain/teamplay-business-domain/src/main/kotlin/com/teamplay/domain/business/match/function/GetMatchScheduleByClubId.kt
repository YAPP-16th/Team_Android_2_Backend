package com.teamplay.domain.business.match.function

import com.teamplay.core.function.Function
import com.teamplay.domain.business.match.dto.MatchScheduleResponse
import com.teamplay.domain.database.jpa.match.repository.MatchRepository
import com.teamplay.domain.database.jpa.match.repository.MatchRequestRepository

class GetMatchScheduleByClubId(
    private val matchRepository: MatchRepository,
    private val matchRequestRepository: MatchRequestRepository
): Function<Long, MatchScheduleResponse> {
    override fun apply(id: Long): MatchScheduleResponse {
        val matchSchedule = matchRepository.findAllAcceptMatch(id)
        val hostMatchRequest = matchRequestRepository.findAllHostMatch(id)
        val guestMatchRequest = matchRequestRepository.findAllGuestMatch(id)

        return MatchScheduleResponse(
                matchSchedule, hostMatchRequest, guestMatchRequest
        )
    }

}
