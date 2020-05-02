package com.teamplay.core.function.error

open class ForbiddenError(message: String = "Forbidden") : CanHaveStatusError(message, 403)