package com.teamplay.domain.business.token.function

import com.teamplay.core.function.Function
import com.teamplay.core.token.Token

class EncodeToken(
    private val secretKey: String
): Function<Token, String> {

    override fun apply(token: Token): String {
        return token.encode(secretKey)
    }
}