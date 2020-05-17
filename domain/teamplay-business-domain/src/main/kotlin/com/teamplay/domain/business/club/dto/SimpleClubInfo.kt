package com.teamplay.domain.business.club.dto

import com.teamplay.domain.database.club.entity.ClubCharacter

data class SimpleClubInfo(
    val characters: MutableList<ClubCharacter>,
    val name: String,
    val location: String,
    val createDate: String,
    val memberCount: Int
)