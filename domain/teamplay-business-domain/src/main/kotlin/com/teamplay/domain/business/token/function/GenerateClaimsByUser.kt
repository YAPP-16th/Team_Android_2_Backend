package com.teamplay.domain.business.token.function

import com.teamplay.core.function.Function
import com.teamplay.core.token.Claims
import com.teamplay.core.token.Jwts
import com.teamplay.domain.database.user.entity.User

class GenerateClaimsByUser : Function<User, Claims> {
    override fun apply(user: User): Claims = Jwts.claims(mapOf("userId" to user.id))
}