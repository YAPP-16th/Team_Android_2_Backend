package com.teamplay.domain.business.user.validator

import com.teamplay.core.function.ValidatorWithError
import com.teamplay.domain.business.user.dto.InputPasswordAndRealPassword
import com.teamplay.domain.business.user.error.PasswordDoNotMatchError
import com.teamplay.domain.business.user.error.UserIsNotExistError
import com.teamplay.domain.database.jpa.user.repository.UserRepository

class ConfirmPasswordMatching
    : ValidatorWithError<InputPasswordAndRealPassword>(PasswordDoNotMatchError()) {

    override fun apply(inputPasswordAndRealPassword: InputPasswordAndRealPassword): Boolean {
        return inputPasswordAndRealPassword.inputPassword == inputPasswordAndRealPassword.realPassword
    }

}