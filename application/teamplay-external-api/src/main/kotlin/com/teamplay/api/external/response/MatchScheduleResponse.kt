package com.teamplay.api.com.teamplay.api.external.response

import com.teamplay.domain.business.match.dto.MatchGuestScheduleInfo
import com.teamplay.domain.business.match.dto.MatchHostScheduleInfo
import com.teamplay.domain.business.match.dto.MatchScheduleInfo

data class MatchScheduleResponse(
    val matchSchedule: MutableList<MutableList<MatchScheduleInfo>>,
    val hostMatchRequest: MutableList<MatchHostScheduleInfo>,
    val guestMatchRequest: MutableList<MatchGuestScheduleInfo>
)
