package com.teamplay.domain.business.token.function

class FindUserIdByAccessToken(
    secretKey: String
) : FindUserIdByToken("AccessToken", secretKey)