package com.teamplay.domain.business.match.dto

import com.teamplay.domain.database.match.entity.MatchRequestStatus

class UpdateMatchRequestDto (
    val matchRequestId: Long,
    val matchRequestStatus: MatchRequestStatus
)
