package com.teamplay.domain.business.token.function

import com.teamplay.core.function.Function
import com.teamplay.core.token.Token
import com.teamplay.domain.database.user.entity.User

class GenerateAccessTokenByUser(
    private val expirationInMs: Int
) : Function<User, Token> {
    private val generateClaimsByUser = GenerateClaimsByUser()
    private val generateAccessToken = GenerateAccessToken(expirationInMs = expirationInMs)
    private val generateRefreshTokenByUser = generateClaimsByUser.andThen(generateAccessToken)

    override fun apply(user: User): Token {
        return generateRefreshTokenByUser(user)
    }
}