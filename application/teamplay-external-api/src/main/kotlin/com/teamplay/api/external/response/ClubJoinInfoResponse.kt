package com.teamplay.api.com.teamplay.api.external.response

import com.teamplay.domain.business.club.dto.ClubInfo
import com.teamplay.domain.business.club.dto.SimpleClubInfo

class ClubJoinInfoResponse(
    val simpleClubInfo : SimpleClubInfo,
    val questions: List<String>,
    override val message: String? = "동호회 가입 정보 불러오기"
): Response()