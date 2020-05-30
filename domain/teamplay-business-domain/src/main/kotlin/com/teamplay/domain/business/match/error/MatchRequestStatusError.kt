package com.teamplay.domain.business.match.error

import com.teamplay.core.function.error.ValidateError

class MatchRequestStatusError(message: String = "match request status is invalid when send this request") : ValidateError(message)

