package com.teamplay.domain.business.user.error

import com.teamplay.core.function.error.NotFoundError

class UserIsNotExistError(message: String = "user is not exist") : NotFoundError(message)