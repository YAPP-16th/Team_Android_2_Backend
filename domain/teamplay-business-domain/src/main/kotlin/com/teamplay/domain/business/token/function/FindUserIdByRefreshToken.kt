package com.teamplay.domain.business.token.function

class FindUserIdByRefreshToken(
    secretKey: String
) : FindUserIdByToken("RefreshToken", secretKey)