package com.teamplay.domain.business.user.error

import com.teamplay.core.function.error.ForbiddenError

class PasswordDoNotMatchError(message: String = "password do not match") : ForbiddenError(message)