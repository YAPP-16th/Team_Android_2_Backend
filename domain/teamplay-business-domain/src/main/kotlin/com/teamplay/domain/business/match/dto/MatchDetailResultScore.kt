package com.teamplay.domain.business.match.dto

import com.teamplay.domain.database.match.entity.MatchResultType

class MatchDetailResultScore (
    val matchResultType: MatchResultType,
    val hostScore: Int,
    val guestScore: Int
)
