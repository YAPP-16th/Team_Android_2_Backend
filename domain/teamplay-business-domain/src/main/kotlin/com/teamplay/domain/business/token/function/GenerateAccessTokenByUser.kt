package com.teamplay.domain.business.token.function

import com.teamplay.core.function.Function
import com.teamplay.core.token.Token
import com.teamplay.domain.database.user.entity.User
import java.util.*

class GenerateAccessTokenByUser(
    private val expirationInMs: Int
) : Function<User, Token> {
    private val generateClaimsByUser = GenerateClaimsByUser()
    private val generateAccessToken = GenerateAccessToken()
    private val generateRefreshTokenByUser = generateClaimsByUser.andThen(generateAccessToken)

    override fun apply(user: User): Token {
        val token = generateRefreshTokenByUser(user)
        val now = Date()
        token.expiration = Date(now.time + expirationInMs)
        token.issuedAt = now
        return token
    }
}