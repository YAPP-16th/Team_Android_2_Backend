package com.teamplay.api.com.teamplay.api.external.response

import com.teamplay.domain.database.match.entity.Match
import com.teamplay.domain.database.match.entity.MatchRequest

data class MatchScheduleResponse(
    val matchSchedule: MutableList<Match>,
    val hostMatchRequest: MutableList<MatchRequest>,
    val guestMatchRequest: MutableList<MatchRequest>,
    override val message: String? = "Match Schedule"
): Response()
