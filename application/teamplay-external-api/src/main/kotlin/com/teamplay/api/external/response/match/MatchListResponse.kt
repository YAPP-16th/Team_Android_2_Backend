package com.teamplay.api.com.teamplay.api.external.response.match

import com.teamplay.domain.business.match.dto.MatchListInfoDTO

class MatchListResponse (
    val currentPage: Int,
    val totalPage: Int,
    val filterTitle: String,
    val matchList: MutableList<MatchListInfoDTO>
)
