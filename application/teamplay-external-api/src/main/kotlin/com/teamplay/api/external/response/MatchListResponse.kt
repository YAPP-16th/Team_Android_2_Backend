package com.teamplay.api.com.teamplay.api.external.response

import com.teamplay.domain.business.match.dto.MatchInfo

data class MatchListResponse(
    val currentPage: Int,
    val totalPage: Int,
    val matchList: MutableList<MatchInfo>
)
