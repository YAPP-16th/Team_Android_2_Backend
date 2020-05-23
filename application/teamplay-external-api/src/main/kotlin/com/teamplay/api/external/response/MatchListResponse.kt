package com.teamplay.api.com.teamplay.api.external.response

import com.teamplay.domain.business.match.dto.MatchList

data class MatchListResponse(
    val currentPage: Int,
    val totalPage: Int,
    val filterTitle: String,
    val matchList: MutableList<MatchList>
)
