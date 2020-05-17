package com.teamplay.api.com.teamplay.api.external.response

import com.teamplay.domain.business.club.dto.*
import com.teamplay.domain.business.user.dto.UserInfo

class ClubResponse(
    val simpleClubInfo: SimpleClubInfo,
    val feedCount: Int,
    val simpleFeeds: List<SimpleFeeds>,
    override val message: String? = "동호회 상세 정보 불러오기"
): Response()