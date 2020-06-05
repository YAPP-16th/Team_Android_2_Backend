package com.teamplay.domain.business.match.dto

import com.teamplay.domain.database.match.entity.MatchRequestStatus

class UpdateMatchRequestDTO (
    val matchRequestId: Long,
    val matchRequestStatus: MatchRequestStatus
)
