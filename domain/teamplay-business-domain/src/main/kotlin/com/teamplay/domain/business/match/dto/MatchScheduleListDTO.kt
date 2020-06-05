package com.teamplay.domain.business.match.dto

import com.teamplay.domain.business.match.dto.type.MatchScheduleType

class MatchScheduleListDTO (
    val scheduleTitle: String,
    val matchScheduleInfo: MutableList<MatchScheduleInfoDTO>,
    val matchScheduleType: MatchScheduleType
)
