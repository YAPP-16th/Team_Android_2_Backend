package com.teamplay.api.com.teamplay.api.external.response

import com.teamplay.domain.business.club.dto.ClubInfo
import com.teamplay.domain.business.club.dto.SimpleClubInfo

class CreateClubInfoResponse(
    val simpleClubInfo : SimpleClubInfo,
    val questions: List<String>,
    override val message: String? = "동호회  생성 정보 불러오기"
): Response()