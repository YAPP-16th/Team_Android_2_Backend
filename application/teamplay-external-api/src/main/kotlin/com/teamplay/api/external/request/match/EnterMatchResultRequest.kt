package com.teamplay.api.com.teamplay.api.external.request.match

import com.teamplay.domain.business.match.dto.DetailResultDTO
import com.teamplay.domain.business.match.dto.IndividualResultDTO

class EnterMatchResultRequest(
    val requesterClubId: Long,
    val matchReview: String? = null,
    val hostScore: Int,
    val guestScore: Int,
    val detailResult: MutableList<DetailResultDTO>? = mutableListOf(),
    val individualResult: MutableList<IndividualResultDTO>? = mutableListOf()
)
