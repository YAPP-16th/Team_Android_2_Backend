package com.teamplay.api.com.teamplay.api.external.request

data class CreateMatchRequest(
    val requesterClubId: Long,
    val requesterUserId: Long,
    val contact: String
)
