package com.teamplay.domain.business.club.function

import com.teamplay.core.function.Function
import com.teamplay.domain.business.club.error.ClubIsNotExistError
import com.teamplay.domain.database.club.entity.Club
import com.teamplay.domain.database.jpa.club.repository.ClubRepository

class FindClubById(
    private val clubRepository: ClubRepository
): Function<Long, Club> {
    override fun apply(clubId: Long): Club {
        return clubRepository.findById(clubId).orElseThrow { throw ClubIsNotExistError() }
    }
}