package com.teamplay.domain.business.match.dto

class MatchDetailResultDto (
    val hostName: String,
    val guestName: String,
    val matchDetailResultScore: MutableList<MatchDetailResultScore>
)
