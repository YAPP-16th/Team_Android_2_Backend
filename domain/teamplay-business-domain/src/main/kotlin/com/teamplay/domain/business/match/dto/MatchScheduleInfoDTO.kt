package com.teamplay.domain.business.match.dto

import com.teamplay.domain.database.match.entity.MatchRequestStatus

class MatchScheduleInfoDTO (
    val title: String,
    val description: String,
    val matchDate: String? = null,
    val matchTime: String? = null,
    val requestStatus: MatchRequestStatus? = null,
    val matchRequestId: Long? = null,
    val matchId: Long? = null
)
