package com.teamplay.domain.business.club.validator

import com.teamplay.core.function.ValidatorWithError
import com.teamplay.domain.business.club.error.ClubIsNotExistError
import com.teamplay.domain.database.jpa.club.repository.ClubRepository

class CheckExistClub(
    private val clubRepository: ClubRepository
): ValidatorWithError<Long>(ClubIsNotExistError()) {

    override fun apply(clubId: Long): Boolean {
        return clubRepository.existsById(clubId)
    }
}