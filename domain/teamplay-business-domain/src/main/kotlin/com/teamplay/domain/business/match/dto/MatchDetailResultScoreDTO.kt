package com.teamplay.domain.business.match.dto

import com.teamplay.domain.database.match.entity.MatchResultType

class MatchDetailResultScoreDTO (
    val matchResultType: MatchResultType,
    val hostScore: Int,
    val guestScore: Int
)
