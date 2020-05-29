package com.teamplay.domain.business.club.error

import com.teamplay.core.function.error.UnauthorizedError

class AdminPermissionError(message: String = "request user is not club admin") : UnauthorizedError(message)
