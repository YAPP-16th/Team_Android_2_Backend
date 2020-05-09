package com.teamplay.api.com.teamplay.api.external.response

class PossibleNicknameResponse(
    val possible: Boolean,
    override val message: String? = "닉네임 중복 체크"
): Response()