package com.teamplay.domain.business.club.error

import com.teamplay.core.function.error.ConflictError

class ClubNameAlreadyExistError(message: String = "name is already exist") : ConflictError(message)