package com.teamplay.domain.business.club.error

import com.teamplay.core.function.error.ConflictError

class UserAlreadyRegisteredClubError(message: String = "user already registered club") : ConflictError(message)