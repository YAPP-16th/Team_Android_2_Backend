package com.teamplay.api.com.teamplay.api.external.response

import com.teamplay.domain.business.user.dto.UserInfo

data class SignInResponse(
    val accessToken: String,
    val refreshToken: String,
    val userInfo: UserInfo,
    override val message: String? = "로그인 정보"
): Response()