package com.teamplay.domain.business.user.validator

import com.teamplay.core.function.ValidatorWithError
import com.teamplay.domain.business.user.error.EmailIsAlreadyRegisteredError
import com.teamplay.domain.database.jpa.user.repository.UserRepository

class CheckDuplicateUserEmail(
    private val repository: UserRepository
): ValidatorWithError<String>(EmailIsAlreadyRegisteredError()) {

    override fun apply(email: String): Boolean {
        return !repository.existsByEmail(email)
    }

}