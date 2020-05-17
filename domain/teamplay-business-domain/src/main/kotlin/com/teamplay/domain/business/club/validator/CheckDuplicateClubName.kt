package com.teamplay.domain.business.club.validator

import com.teamplay.core.function.ValidatorWithError
import com.teamplay.domain.business.club.error.ClubNameAlreadyExistError
import com.teamplay.domain.database.jpa.club.repository.ClubRepository

class CheckDuplicateClubName(
    private val clubRepository: ClubRepository
): ValidatorWithError<String>(ClubNameAlreadyExistError()) {

    override fun apply(name: String): Boolean {
        return !clubRepository.existsByName(name)
    }

}