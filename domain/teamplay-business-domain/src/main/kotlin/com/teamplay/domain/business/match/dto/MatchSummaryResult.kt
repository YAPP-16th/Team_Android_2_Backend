package com.teamplay.domain.business.match.dto

class MatchSummaryResult (
    val matchClubType: MatchClubType,
    val totalScore: Int,
    val recentlyRecord: MutableList<Boolean>,
    val isVictory: Boolean
)
