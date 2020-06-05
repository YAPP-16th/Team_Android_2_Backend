package com.teamplay.domain.business.match.dto

import com.teamplay.domain.database.match.entity.MatchStyle
import java.util.*

class CreateMatchDTO (
    val title: String,
    val startDate: Date,
    val endDate: Date,
    val location: String,
    val matchStyle: MatchStyle,
    val introduce: String
)
