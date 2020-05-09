package com.teamplay.domain.business.club.function

import com.teamplay.core.function.Function
import com.teamplay.domain.database.club.entity.ClubAdmin
import com.teamplay.domain.database.jpa.club.repository.ClubAdminRepository
import com.teamplay.domain.database.jpa.club.repository.ClubRepository

class RegisterAdmin(
    private val clubAdminRepository: ClubAdminRepository
): Function<ClubAdmin, ClubAdmin> {
    override fun apply(clubAdmin: ClubAdmin): ClubAdmin {
        return clubAdminRepository.save(clubAdmin)
    }
}