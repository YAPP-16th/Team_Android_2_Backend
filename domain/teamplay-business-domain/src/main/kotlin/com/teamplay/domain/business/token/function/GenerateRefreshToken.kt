package com.teamplay.domain.business.token.function

import com.teamplay.core.token.Claims
import com.teamplay.core.token.Jwts

class GenerateRefreshToken(
    private val defaultClams: Claims = Jwts.claims()
): GenerateToken(
    Jwts.claims().apply {
        putAll(defaultClams)
        subject = "RefreshToken"
    }
)