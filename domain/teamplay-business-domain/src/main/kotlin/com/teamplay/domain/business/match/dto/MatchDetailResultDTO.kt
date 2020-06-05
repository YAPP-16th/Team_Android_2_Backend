package com.teamplay.domain.business.match.dto

class MatchDetailResultDTO (
    val hostName: String,
    val guestName: String,
    val matchDetailResultScore: MutableList<MatchDetailResultScoreDTO>
)
