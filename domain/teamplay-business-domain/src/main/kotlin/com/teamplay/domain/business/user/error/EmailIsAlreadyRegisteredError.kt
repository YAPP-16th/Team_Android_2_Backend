package com.teamplay.domain.business.user.error

import com.teamplay.core.function.error.ConflictError

class EmailIsAlreadyRegisteredError(message: String = "email is already registered") : ConflictError(message)