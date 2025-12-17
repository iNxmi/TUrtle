package de.csw.turtle.api.dto.get

import java.time.Instant

data class GetExceptionResponse(
    override val id: Long,

    val endpoint: String?,
    val exception: String?,
    val message: String?,
    val stackTrace: String?,

    override val createdAt: Instant
) : GetResponse