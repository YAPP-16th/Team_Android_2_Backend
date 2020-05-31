package com.teamplay.api.com.teamplay.api.external.request

import com.teamplay.domain.business.match.dto.DetailResult
import com.teamplay.domain.business.match.dto.IndividualResult

data class EnterMatchResultRequest(
    val matchId: Long,
    val matchReview: String? = null,
    val homeScore: Int,
    val awayScore: Int,
    val detailResult: MutableList<DetailResult>? = mutableListOf(),
    val individualResult: MutableList<IndividualResult>? = mutableListOf()
)
