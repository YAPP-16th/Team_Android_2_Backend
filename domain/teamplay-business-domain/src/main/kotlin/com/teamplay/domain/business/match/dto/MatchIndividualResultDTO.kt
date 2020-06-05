package com.teamplay.domain.business.match.dto

import com.teamplay.domain.database.match.entity.MatchResultType

class MatchIndividualResultDTO (
    val matchResultType: MatchResultType,
    val score: Int,
    val recevier: String
)
