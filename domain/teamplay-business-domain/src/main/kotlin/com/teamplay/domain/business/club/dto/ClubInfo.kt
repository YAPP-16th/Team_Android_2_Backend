package com.teamplay.domain.business.club.dto

import com.teamplay.domain.database.club.entity.Category
import com.teamplay.domain.database.club.entity.Club
import com.teamplay.domain.database.club.entity.ClubCharacter
import com.teamplay.domain.database.user.entity.Ability
import java.util.*

class ClubInfo constructor(
    val clubId: Long,
    val name: String,
    val category: Category,
    val location: String? = null,
    val emblem: String? = null,
    val ability: Ability,
    val thumbnail: String? = null,
    val introduce: String? = null,
    val contact: String? = null,
    val createDate: Date,
    val updateDate: Date,
    val createTeamDate: String,
    val characters: MutableList<ClubCharacter>,
    val questions: MutableList<String>
){
    constructor(club: Club): this(
        club.id!!,
        club.name,
        club.category,
        club.location,
        club.emblem,
        club.ability,
        club.thumbnail,
        club.introduce,
        club.contact,
        club.createdDate,
        club.updatedDate!!,
        club.createTeamDate,
        club.characters,
        club.questions
    )
}
