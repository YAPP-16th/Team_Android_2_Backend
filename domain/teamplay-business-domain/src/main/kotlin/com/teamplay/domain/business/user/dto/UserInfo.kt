package com.teamplay.domain.business.user.dto

import com.teamplay.domain.database.user.entity.User

class UserInfo constructor(
    val id: Long?,
    val nickname: String?,
    val email: String?,
    val name: String? = null
){
    constructor(user: User): this(
        user.id!!,
        user.nickname,
        user.email,
        user.name
    )
}