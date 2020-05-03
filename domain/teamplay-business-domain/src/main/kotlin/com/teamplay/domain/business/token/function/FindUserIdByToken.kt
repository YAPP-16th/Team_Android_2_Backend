package com.teamplay.domain.business.token.function

import com.teamplay.core.function.Function
import com.teamplay.domain.business.token.error.TokenIsInvalidError

open class FindUserIdByToken (
    private val subject: String,
    private val secretKey: String
): Function<String, Long> {
    private val decodeToken = DecodeToken(secretKey)

    override fun apply(jwt: String): Long {
        val token = decodeToken(jwt)
        if(token.subject != subject) throw TokenIsInvalidError("Token does not match subject")

        return token["userId"].toString().toLongOrNull() ?: throw TokenIsInvalidError()
    }
}