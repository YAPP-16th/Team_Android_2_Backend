package com.teamplay.domain.business.club.function

import com.teamplay.core.function.Function
import com.teamplay.domain.database.club.entity.Club
import com.teamplay.domain.database.jpa.club.repository.ClubRepository

class CreateClub(
    private val clubRepository: ClubRepository
): Function<Club, Club> {
    override fun apply(club: Club): Club {
        return clubRepository.save(club)
    }
}