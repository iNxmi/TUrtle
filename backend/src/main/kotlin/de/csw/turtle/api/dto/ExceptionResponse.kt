package de.csw.turtle.api.dto

import org.springframework.http.HttpStatus
import java.time.Instant

data class ExceptionResponse(
    val url: String,
    val status: StatusDetails,
    val exception: ExceptionDetails,
    val timestamp: String = Instant.now().toString()
) {

    constructor(url: String, exception: Exception, status: HttpStatus) : this(
        url = url,
        status = StatusDetails(status),
        exception = ExceptionDetails(exception)
    )

    data class ExceptionDetails(
        val name: String?,
        val message: String?
    ) {
        constructor(exception: Exception) : this(
            name = exception::class.simpleName,
            message = exception.message
        )
    }

    data class StatusDetails(
        val code: Int,
        val name: String
    ) {
        constructor(status: HttpStatus) : this(
            code = status.value(),
            name = status.name
        )
    }

}