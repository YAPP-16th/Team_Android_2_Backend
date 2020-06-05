package com.teamplay.domain.business.match.dto

import com.teamplay.domain.business.match.dto.type.MatchClubType

class MatchSummaryResultDTO (
    val matchClubType: MatchClubType,
    val totalScore: Int,
    val recentlyRecord: MutableList<Boolean>,
    val isVictory: Boolean
)
