package com.teamplay.domain.business.match.dto

import com.teamplay.domain.database.match.entity.MatchResultType

class DetailResult (
    val hostScore: Int,
    val guestScore: Int,
    val matchResultType: MatchResultType
)
