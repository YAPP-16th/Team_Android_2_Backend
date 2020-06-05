package com.teamplay.api.com.teamplay.api.external.request.match

import com.teamplay.domain.business.match.dto.CreateMatchDTO

class CreateMatch(
    val requesterClubId: Long,
    val createMatchDTO: CreateMatchDTO
)
