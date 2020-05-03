package com.teamplay.domain.business.user.validator

import com.teamplay.core.function.ValidatorWithError
import com.teamplay.domain.business.user.error.UserIsNotExistError
import com.teamplay.domain.database.jpa.user.repository.UserRepository

class CheckExistUserById(
    private val repository: UserRepository
): ValidatorWithError<Long>(UserIsNotExistError()) {

    override fun apply(userId: Long): Boolean {
        return repository.existsById(userId)
    }
}