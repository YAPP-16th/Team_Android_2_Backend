package com.teamplay.domain.business.club.dto

data class SimpleClubInfo(
    val tag: String,
    val name: String,
    val location: String,
    val createDate: String,
    val memberCount: Int
)