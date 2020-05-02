package com.teamplay.domain.business.token.function

import com.teamplay.core.function.Function
import com.teamplay.domain.database.user.entity.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts

class GenerateClaimsByUser : Function<User, Claims> {
    override fun apply(user: User): Claims = Jwts.claims(mapOf("userId" to user.id))
}