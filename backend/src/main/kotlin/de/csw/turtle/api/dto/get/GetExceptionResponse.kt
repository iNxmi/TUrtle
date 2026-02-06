package de.csw.turtle.api.dto.get

import de.csw.turtle.api.entity.ExceptionEntity
import java.time.Instant

data class GetExceptionResponse(
    override val id: Long?,

    val endpoint: String?,
    val exception: String?,
    val message: String?,
    val stackTrace: String?,

    override val updatedAt: Instant?,
    override val createdAt: Instant?
) : GetResponse {

    constructor(entity: ExceptionEntity) : this(
        id = entity.id,

        endpoint = entity.endpoint,
        exception = entity.exception,
        message = entity.message,
        stackTrace = entity.stackTrace,

        updatedAt = entity.updatedAt,
        createdAt = entity.createdAt
    )

}