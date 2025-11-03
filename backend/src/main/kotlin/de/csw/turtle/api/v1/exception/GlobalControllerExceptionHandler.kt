package de.csw.turtle.api.v1.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.servlet.resource.NoResourceFoundException
import java.time.Instant

@ControllerAdvice
class GlobalControllerExceptionHandler {

    companion object {
        fun payload(exception: Exception, status: HttpStatus) = mapOf(
            "exception" to mapOf(
                "message" to exception.message,
                "type" to exception::class.simpleName
            ),
            "status" to mapOf(
                "code" to status.value(),
                "type" to status
            ),
            "timestamp" to Instant.now().toString()
        )
    }

    @ExceptionHandler(TUrtleException::class)
    fun turtle(exception: TUrtleException) = exception.responseEntity(exception.status)

    @ExceptionHandler(NoResourceFoundException::class)
    fun noResourceFound(exception: NoResourceFoundException) = exception.responseEntity(HttpStatus.NOT_FOUND)

    @ExceptionHandler(Exception::class)
    fun generic(exception: Exception): ResponseEntity<Map<String, Any>> {
        exception.printStackTrace()
        return exception.responseEntity(HttpStatus.INTERNAL_SERVER_ERROR)
    }

    private fun Exception.responseEntity(status: HttpStatus): ResponseEntity<Map<String, Any>> {
        val payload = payload(this, status)
        return ResponseEntity.status(status).body(payload)
    }

}