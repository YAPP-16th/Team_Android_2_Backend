package com.teamplay.api.com.teamplay.api.external.response

import com.teamplay.domain.business.match.dto.MatchScheduleList

data class MatchScheduleResponse(
    val matchSchedule: MutableList<MatchScheduleList>
)
