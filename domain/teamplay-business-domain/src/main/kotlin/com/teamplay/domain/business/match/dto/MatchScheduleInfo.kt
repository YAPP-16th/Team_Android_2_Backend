package com.teamplay.domain.business.match.dto

import com.teamplay.domain.database.match.entity.MatchStyle
import java.util.*

class MatchScheduleInfo (
    val homeName: String,
    val awayName: String,
    val matchStyle: MatchStyle,
    val location: String,
    val matchStartTime: Date,
    val matchEndTime: Date
)
