package com.teamplay.api.com.teamplay.api.external.request

import com.teamplay.domain.database.club.entity.Category
import com.teamplay.domain.database.user.entity.Ability

data class CreateClubRequest(
    val name: String,
    val category: Category,
    val emblem: String? = null,
    val location: String,
    val ability: Ability,
    val thumbnail: String? = null,
    val introduce: String? = null,
    val contact: String? = null,
    val createTeamDate: String,
    val tags: MutableList<String>,
    val questions: MutableList<String>
)