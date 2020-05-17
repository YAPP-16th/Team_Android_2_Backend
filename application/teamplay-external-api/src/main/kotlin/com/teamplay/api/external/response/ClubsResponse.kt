package com.teamplay.api.com.teamplay.api.external.response

import com.teamplay.domain.business.club.dto.ClubInfo

class ClubsResponse(
    val clubInfos: MutableList<ClubInfo>,
    val totalPage: Int,
    val currentPage: Int,
    override val message: String? = "동호회 목록 불러오기"
): Response()