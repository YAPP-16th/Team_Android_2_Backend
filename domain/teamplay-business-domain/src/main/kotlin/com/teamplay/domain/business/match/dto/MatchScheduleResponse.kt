package com.teamplay.domain.business.match.dto

import com.teamplay.domain.database.match.entity.Match
import com.teamplay.domain.database.match.entity.MatchRequest

data class MatchScheduleResponse(
    val matchSchedule: MutableList<Match>,
    val hostMatchRequest: MutableList<MatchRequest>,
    val guestMatchRequest: MutableList<MatchRequest>
)
