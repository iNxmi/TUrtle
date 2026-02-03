package de.csw.turtle.api.dto.get

import java.time.Instant

data class GetUserResponse(
    override val id: Long?,

    val username: String?,
    val firstName: String?,
    val lastName: String?,
    val email: String?,
    val emojis: String,
    val roleIds: Set<Long>?,
    val verified: Boolean?,

    override val updatedAt: Instant?,
    override val createdAt: Instant?
) : GetResponse