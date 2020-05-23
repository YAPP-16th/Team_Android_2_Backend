package com.teamplay.domain.business.match.dto

import com.teamplay.domain.database.match.entity.MatchStyle
import java.util.*

class MatchInfo (
    val title: String,
    val hostName: String,
    val matchStartTime: Date,
    val matchEndTime: Date,
    val location: String,
    val matchStyle: MatchStyle,
    val introduce: String
)
