package com.teamplay.core.function

import org.jetbrains.annotations.Contract

abstract class ValidatorWithError<T>(private val error: Error) : Validator<T> {
    // Please Use When T Is Unit
    @Contract(pure = true)
    override fun verify() {
        verify(error)
    }

    @Contract(pure = true)
    override fun verify(var1: T) {
        verify(var1, error)
    }
}