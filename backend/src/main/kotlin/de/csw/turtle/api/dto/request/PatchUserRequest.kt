package de.csw.turtle.api.dto.request

import de.csw.turtle.api.security.Role

data class PatchUserRequest(
    val username: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val studentId: Long? = null,
    val role: Role? = null,
    val password: String? = null
)
