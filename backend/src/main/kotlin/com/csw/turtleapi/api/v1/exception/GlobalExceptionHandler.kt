package com.csw.turtleapi.api.v1.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.Instant

@ControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(TUrtleException::class)
    fun userNotFound(exception: TUrtleException) = exception.responseEntity(exception.status)

    @ExceptionHandler(Exception::class)
    fun generic(exception: Exception) = exception.responseEntity(HttpStatus.INTERNAL_SERVER_ERROR)

    private fun Exception.responseEntity(status: HttpStatus): ResponseEntity<Map<String, Any>> {
        val body = mapOf(
            "exception" to mapOf(
                "message" to this.message,
                "type" to this::class.simpleName
            ),
            "status" to mapOf(
                "code" to status.value(),
                "type" to status
            ),
            "timestamp" to Instant.now().toString()
        )

        return ResponseEntity.status(status).body(body)
    }

}