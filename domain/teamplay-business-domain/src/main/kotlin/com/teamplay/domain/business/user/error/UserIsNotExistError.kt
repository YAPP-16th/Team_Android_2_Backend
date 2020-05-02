package com.teamplay.domain.business.user.error

import com.teamplay.core.function.error.ConflictError
import com.teamplay.core.function.error.NotFoundError

class UserIsNotExistError(message: String = "user is not exist error") : NotFoundError(message)