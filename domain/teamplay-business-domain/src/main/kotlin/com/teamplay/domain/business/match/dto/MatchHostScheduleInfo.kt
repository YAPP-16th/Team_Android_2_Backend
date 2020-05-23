package com.teamplay.domain.business.match.dto

import com.teamplay.domain.database.match.entity.MatchStyle

class MatchHostScheduleInfo (
    val hostName: String,
    val matchStyle: MatchStyle,
    val location: String
)
