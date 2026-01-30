package de.csw.turtle.api.dto

import org.springframework.http.HttpStatus
import java.time.Instant

data class ExceptionResponse(
    val url: String,
    val message: String,
    val status: StatusDetails,
    val timestamp: String = Instant.now().toString()
) {

    constructor(url: String, message: String, status: HttpStatus) : this(
        url = url,
        message = message,
        status = StatusDetails(status)
    )

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