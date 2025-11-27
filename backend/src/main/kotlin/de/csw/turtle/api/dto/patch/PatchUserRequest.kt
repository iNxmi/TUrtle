package de.csw.turtle.api.dto.patch

import de.csw.turtle.api.Role

data class PatchUserRequest(
    val username: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val studentId: Long? = null,
    val role: Role? = null,
    val password: String? = null
) : CRUDPatchRequest
