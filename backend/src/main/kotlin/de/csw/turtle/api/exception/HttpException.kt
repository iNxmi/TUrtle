package de.csw.turtle.api.exception

import org.springframework.http.HttpStatus

sealed class HttpException(
    val status: HttpStatus,
    message: String? = null
) : RuntimeException(message ?: "${status.value()} ${status.reasonPhrase}") {

    class ServiceUnavailable(message: String? = null) : HttpException(HttpStatus.SERVICE_UNAVAILABLE, message)
    class Unauthorized(message: String? = null) : HttpException(HttpStatus.UNAUTHORIZED, message)
    class NotFound(message: String? = null) : HttpException(HttpStatus.NOT_FOUND, message)
    class InternalServerError(message: String? = null) : HttpException(HttpStatus.INTERNAL_SERVER_ERROR, message)
    class Forbidden(message: String? = null) : HttpException(HttpStatus.FORBIDDEN, message)
    class Conflict(message: String? = null) : HttpException(HttpStatus.CONFLICT, message)
    class BadRequest(message: String? = null) : HttpException(HttpStatus.BAD_REQUEST, message)
    class BadGateway(message: String? = null) : HttpException(HttpStatus.BAD_GATEWAY, message)
    class GatewayTimeout(message: String? = null) : HttpException(HttpStatus.GATEWAY_TIMEOUT, message)
    class Gone(message: String? = null) : HttpException(HttpStatus.GONE, message)

}