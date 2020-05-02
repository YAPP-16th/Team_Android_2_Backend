package com.teamplay.domain.business.user.error

import com.teamplay.core.function.error.ConflictError

class NicknameAlreadyRegisteredError(message: String = "nickname is already registered") : ConflictError(message)