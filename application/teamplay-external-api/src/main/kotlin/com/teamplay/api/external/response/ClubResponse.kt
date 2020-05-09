package com.teamplay.api.com.teamplay.api.external.response

import com.teamplay.domain.business.club.dto.ClubInfo
import com.teamplay.domain.business.user.dto.UserInfo

class ClubResponse(
    val clubInfo: ClubInfo,
    val admins: MutableList<UserInfo>,
    val members: MutableList<UserInfo>,
    override val message: String? = "동호회 상세 정보 불러오기"
): Response()