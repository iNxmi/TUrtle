package de.csw.turtle.api.dto.get

import de.csw.turtle.api.security.Role
import java.time.Instant

data class GetUserResponse(
    override val id: Long,
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val studentId: Long,
    val role: Role,
    val createdAt: Instant
) : CRUDGetResponse