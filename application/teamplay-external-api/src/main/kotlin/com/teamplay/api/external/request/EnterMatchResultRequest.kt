package com.teamplay.api.com.teamplay.api.external.request

import com.teamplay.domain.business.match.dto.DetailResult
import com.teamplay.domain.business.match.dto.IndividualResult

data class EnterMatchResultRequest(
    val requesterUserId: Long,
    val requesterClubId: Long,
    val matchReview: String? = null,
    val hostScore: Int,
    val guestScore: Int,
    val detailResult: MutableList<DetailResult>? = mutableListOf(),
    val individualResult: MutableList<IndividualResult>? = mutableListOf()
)
