package com.teamplay.domain.business.match.error

import com.teamplay.core.function.error.ValidateError

class MatchStatusError(message: String = "match status is invalid when send this request") : ValidateError(message)

