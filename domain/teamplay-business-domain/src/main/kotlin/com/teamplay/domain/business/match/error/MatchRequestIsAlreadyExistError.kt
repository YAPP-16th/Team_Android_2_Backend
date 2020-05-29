package com.teamplay.domain.business.match.error

import com.teamplay.core.function.error.ConflictError

class MatchRequestIsAlreadyExistError(message: String = "match request is already exist") : ConflictError(message)

