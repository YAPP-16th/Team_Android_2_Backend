package com.teamplay.domain.business.token.function

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import java.util.*

class GenerateAccessToken(
    private val defaultClams: Claims = Jwts.claims()
): GenerateToken(
    Jwts.claims().apply {
        putAll(defaultClams)
        subject = "AccessToken"
    }
)