package com.teamplay.api.com.teamplay.api.external.response

class PossibleEmailResponse(
    val possible: Boolean,
    override val message: String? = "이메일 중복 체크"
): Response()