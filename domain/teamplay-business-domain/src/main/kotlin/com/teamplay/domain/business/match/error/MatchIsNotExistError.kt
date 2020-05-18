package com.teamplay.domain.business.match.error

import com.teamplay.core.function.error.NotFoundError

class MatchIsNotExistError(message: String = "match is not exist") : NotFoundError(message)

