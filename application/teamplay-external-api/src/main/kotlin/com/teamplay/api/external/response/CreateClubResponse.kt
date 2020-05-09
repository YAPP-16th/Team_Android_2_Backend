package com.teamplay.api.com.teamplay.api.external.response

import com.teamplay.domain.business.club.dto.ClubInfo
import com.teamplay.domain.business.user.dto.UserInfo

data class CreateClubResponse(
    val clubInfo: ClubInfo,
    val clubAdmin: MutableList<UserInfo>,
    val clubMember: MutableList<UserInfo>,
    override val message: String? = "동호회 생성 완료"
): Response()