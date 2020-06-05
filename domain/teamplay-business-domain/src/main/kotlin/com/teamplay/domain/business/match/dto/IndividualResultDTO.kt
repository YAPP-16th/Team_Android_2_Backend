package com.teamplay.domain.business.match.dto

import com.teamplay.domain.database.match.entity.MatchResultType

class IndividualResultDTO (
    val score: Int,
    val receiver: String,
    val matchResultType: MatchResultType
)
