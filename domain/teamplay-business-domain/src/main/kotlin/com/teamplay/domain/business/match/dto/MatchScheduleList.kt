package com.teamplay.domain.business.match.dto

class MatchScheduleList (
    val scheduleTitle: String,
    val matchScheduleInfo: MutableList<MatchScheduleInfo>,
    val matchScheduleType: MatchScheduleType
)
