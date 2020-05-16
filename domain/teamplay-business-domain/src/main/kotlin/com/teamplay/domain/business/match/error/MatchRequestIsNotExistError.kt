package com.teamplay.domain.business.match.error

import com.teamplay.core.function.error.NotFoundError

class MatchRequestIsNotExistError(message: String = "match request is not exist") : NotFoundError(message)

