package com.teamplay.core.function.error

open class NotFoundError(message: String = "Not Found.") : CanHaveStatusError(message, 404)
