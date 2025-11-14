package de.csw.turtle.api.dto.response

import de.csw.turtle.api.entity.ExceptionEntity
import java.time.Instant

data class GetExceptionResponse(
    val id: Long,
    val endpoint: String,
    val exception: String?,
    val message: String?,
    val stackTrace: String,
    val createdAt: Instant
) {

    constructor(exception: ExceptionEntity) : this(
        id = exception.id,
        endpoint = exception.endpoint,
        exception = exception.exception,
        message = exception.message,
        stackTrace = exception.stackTrace,
        createdAt = exception.createdAt,
    )

}