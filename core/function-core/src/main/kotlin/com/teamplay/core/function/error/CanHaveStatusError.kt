package com.teamplay.core.function.error

open class CanHaveStatusError(message: String, val status: Int? = null) : Error(message)