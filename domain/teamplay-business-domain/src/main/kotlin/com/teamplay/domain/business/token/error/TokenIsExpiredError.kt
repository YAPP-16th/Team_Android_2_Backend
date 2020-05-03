package com.teamplay.domain.business.token.error

import com.teamplay.core.function.error.UnauthorizedError

class TokenIsExpiredError(message: String = "Token Is Invalid") : UnauthorizedError(message)