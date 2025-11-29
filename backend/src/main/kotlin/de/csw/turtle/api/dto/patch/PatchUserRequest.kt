package de.csw.turtle.api.dto.patch

data class PatchUserRequest(
    val username: String? = null,
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val studentId: Long? = null,
    val roleIds: Set<Long>? = null,
    val password: String? = null
) : CRUDPatchRequest
