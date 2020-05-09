package com.teamplay.domain.business.club.dto

import org.springframework.data.domain.Pageable

data class NameAndPage(
    val name: String = "",
    val page: Int
)