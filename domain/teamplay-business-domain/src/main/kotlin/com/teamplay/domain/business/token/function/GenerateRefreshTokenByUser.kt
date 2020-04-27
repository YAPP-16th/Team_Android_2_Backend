package com.teamplay.domain.business.token.function

import com.teamplay.core.function.Function
import com.teamplay.core.token.Token
import com.teamplay.domain.database.user.entity.User

class GenerateRefreshTokenByUser : Function<User, Token> {
    private val generateClaimsByUser = GenerateClaimsByUser()
    private val generateRefreshToken = GenerateRefreshToken()
    private val generateRefreshTokenByUser = generateClaimsByUser.andThen(generateRefreshToken)

    override fun apply(user: User): Token {
        return generateRefreshTokenByUser(user)
    }
}