package com.teamplay.domain.business.match.dto

import com.teamplay.domain.database.match.entity.MatchRequestStatus
import com.teamplay.domain.database.match.entity.MatchStyle

class MatchGuestScheduleInfo (
    val requesterName: String,
    val matchStyle: MatchStyle,
    val location: String,
    val requestStatus: MatchRequestStatus
)
