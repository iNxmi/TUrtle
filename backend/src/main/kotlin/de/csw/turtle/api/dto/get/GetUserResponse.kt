package de.csw.turtle.api.dto.get

import java.time.Instant

data class GetUserResponse(
    override val id: Long,
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val roles: Set<Long>,
    override val createdAt: Instant
) : GetResponse