package de.csw.turtle.api.dto.get

import de.csw.turtle.api.entity.TokenEntity
import java.time.Duration
import java.time.Instant

data class GetTokenResponse(
    override val id: Long,
    val code: String,
    val duration: Duration,
    val type: TokenEntity.Type,
    override val updatedAt: Instant,
    override val createdAt: Instant
) : GetResponse {

    constructor(token: TokenEntity) : this(
        id = token.id,
        code = token.code,
        duration = token.duration,
        type = token.type,
        updatedAt = token.updatedAt,
        createdAt = token.createdAt
    )

}