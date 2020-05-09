package com.teamplay.domain.business.user.validator

import com.teamplay.core.function.ValidatorWithError
import com.teamplay.domain.business.user.error.UserIsNotExistError
import com.teamplay.domain.database.jpa.user.repository.UserRepository

class CheckExistUserEmail(
    private val repository: UserRepository
): ValidatorWithError<String>(UserIsNotExistError()) {
    override fun apply(email: String): Boolean {
        return repository.existsByEmail(email)
    }
}