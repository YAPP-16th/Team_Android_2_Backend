package com.teamplay.domain.business.user.validator

import com.teamplay.core.function.ValidatorWithError
import com.teamplay.domain.business.user.error.NicknameAlreadyRegisteredError
import com.teamplay.domain.database.jpa.user.repository.UserRepository

class CheckDuplicateUserNickname(
    private val repository: UserRepository
): ValidatorWithError<String>(NicknameAlreadyRegisteredError()) {

    override fun apply(nickname: String): Boolean {
        return !repository.existsByNickname(nickname)
    }
}