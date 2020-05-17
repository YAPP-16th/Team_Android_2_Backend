package com.teamplay.domain.business.club.dto

import com.teamplay.domain.database.club.entity.ClubCharacter

data class CharactersAndPage(
    val characters: List<ClubCharacter>,
    val page: Int
)