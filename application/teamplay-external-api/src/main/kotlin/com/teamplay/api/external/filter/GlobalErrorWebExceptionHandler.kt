package com.teamplay.api.com.teamplay.api.external.filter

import com.teamplay.api.com.teamplay.api.external.response.ErrorResponse
import com.teamplay.core.function.error.CanHaveStatusError
import com.teamplay.core.function.error.ConflictError
import com.teamplay.core.function.error.ForbiddenError
import com.teamplay.core.function.error.NotFoundError
import org.slf4j.LoggerFactory
import org.springframework.core.annotation.Order
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.server.ServerWebExchange
import org.springframework.web.server.WebExceptionHandler
import org.springframework.web.servlet.function.ServerResponse


@ControllerAdvice
@RestController
class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(value = [ConflictError::class])
    fun conflictException(error : ConflictError) = error.message!!

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = [NotFoundError::class])
    fun notFoundException(error : NotFoundError) = error.message!!

    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler(value = [ForbiddenError::class])
    fun forbiddenException(error : ForbiddenError) = error.message!!

}