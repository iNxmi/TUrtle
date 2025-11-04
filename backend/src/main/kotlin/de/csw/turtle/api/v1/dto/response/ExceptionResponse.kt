package de.csw.turtle.api.v1.dto.response

import org.springframework.http.HttpStatus
import java.time.Instant

data class ExceptionDetails(
    val type: String?,
    val message: String?
) {
    constructor(exception: Exception) : this(
        exception::class.simpleName,
        exception.message
    )
}

data class StatusDetails(
    val status: Int,
    val type: String
) {
    constructor(status: HttpStatus) : this(status.value(), status.name)
}

data class ExceptionResponse(
    val url: String,
    val status: StatusDetails,
    val exception: ExceptionDetails,
    val timestamp: String = Instant.now().toString()
) {
    constructor(url: String, exception: Exception, status: HttpStatus) : this(
        url,
        StatusDetails(status),
        ExceptionDetails(exception)
    )
}