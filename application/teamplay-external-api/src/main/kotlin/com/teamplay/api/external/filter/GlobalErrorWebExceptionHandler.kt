package com.teamplay.api.com.teamplay.api.external.filter

import com.teamplay.core.function.error.ConflictError
import com.teamplay.core.function.error.ForbiddenError
import com.teamplay.core.function.error.NotFoundError
import com.teamplay.core.function.error.UnauthorizedError
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController


@ControllerAdvice
@RestController
class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(value = [ConflictError::class])
    fun conflictException(error : ConflictError) = error.message!!

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = [NotFoundError::class])
    fun notFoundException(error : NotFoundError) = error.message!!

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(value = [UnauthorizedError::class])
    fun unauthorizedException(error : UnauthorizedError) = error.message!!

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = [ForbiddenError::class])
    fun forbiddenException(error : ForbiddenError) = error.message!!
}
