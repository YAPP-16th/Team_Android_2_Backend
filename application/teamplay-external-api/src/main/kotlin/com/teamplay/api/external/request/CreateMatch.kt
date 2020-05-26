package com.teamplay.api.com.teamplay.api.external.request

import com.teamplay.domain.business.match.dto.CreateMatchDto

data class CreateMatch(
    val requesterUserId: Long,
    val requesterClubId: Long,
    val createMatchDto: CreateMatchDto
)
