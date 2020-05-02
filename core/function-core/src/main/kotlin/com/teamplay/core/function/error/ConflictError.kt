package com.teamplay.core.function.error

open class ConflictError(message: String = "Conflict") : CanHaveStatusError(message, 409)