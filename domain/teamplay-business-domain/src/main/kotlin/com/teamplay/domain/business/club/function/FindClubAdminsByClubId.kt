package com.teamplay.domain.business.club.function

import com.teamplay.core.function.Function
import com.teamplay.domain.database.club.entity.ClubAdmin
import com.teamplay.domain.database.jpa.club.repository.ClubAdminRepository

class FindClubAdminsByClubId(
    private val clubAdminRepository: ClubAdminRepository
): Function<Long, MutableList<ClubAdmin>> {
    override fun apply(clubId: Long): MutableList<ClubAdmin> {
        return clubAdminRepository.findAllByClubId(clubId)
    }
}