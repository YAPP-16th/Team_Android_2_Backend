package com.teamplay.api.com.teamplay.api.external.request

data class GetClubsByAddressRequest(
    val address: String = "",
    val currentPage: Int
)