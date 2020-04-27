package com.teamplay.api.com.teamplay.api.external.response

data class ErrorResponse(override val message: String? = null) : Response(message)