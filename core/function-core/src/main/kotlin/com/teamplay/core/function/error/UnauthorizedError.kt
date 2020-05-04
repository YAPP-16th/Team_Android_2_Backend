package com.teamplay.core.function.error

open class UnauthorizedError (message: String = "UnauthorizedError.") : CanHaveStatusError(message, 401)