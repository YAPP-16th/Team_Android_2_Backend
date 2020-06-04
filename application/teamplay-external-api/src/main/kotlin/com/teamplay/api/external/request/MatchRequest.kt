package com.teamplay.api.com.teamplay.api.external.request

import com.teamplay.domain.business.match.dto.MatchDTO.*

class MatchRequest {
    class EnterMatchResultRequest(
        val requesterClubId: Long,
        val matchReview: String? = null,
        val hostScore: Int,
        val guestScore: Int,
        val detailResult: MutableList<DetailResultDTO>? = mutableListOf(),
        val individualResult: MutableList<IndividualResultDTO>? = mutableListOf()
    )

    class CreateMatchRequest(
        val requesterClubId: Long,
        val contact: String
    )

    class CreateMatch(
        val requesterClubId: Long,
        val createMatchDTO: CreateMatchDTO
    )
}
