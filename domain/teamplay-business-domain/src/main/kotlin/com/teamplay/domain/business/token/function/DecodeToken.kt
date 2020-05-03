package com.teamplay.domain.business.token.function

import com.teamplay.core.function.Function
import com.teamplay.core.token.Token
import com.teamplay.domain.business.token.error.TokenIsExpiredError
import com.teamplay.domain.business.token.error.TokenIsInvalidError
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.MalformedJwtException
import io.jsonwebtoken.UnsupportedJwtException

class DecodeToken(
    private val secretKey: String
): Function<String, Token> {

    override fun apply(jwt: String): Token {
        try {
            return Token.decode(jwt, secretKey)
        } catch (ex: MalformedJwtException) {
            throw TokenIsInvalidError(message = "Invalid JWT token.")
        } catch (ex: ExpiredJwtException) {
            throw TokenIsExpiredError(message = "Expired JWT token.")
        } catch (ex: UnsupportedJwtException) {
            throw TokenIsInvalidError(message = "Unsupported JWT token.")
        } catch (ex: IllegalArgumentException) {
            throw TokenIsInvalidError(message = "JWT claims string is empty.")
        } catch (ex: Throwable) {
            throw TokenIsInvalidError()
        }
    }
}