package de.csw.turtle.api.dto.patch

data class PatchProfileRequest(
    val firstName: String? = null,
    val lastName: String? = null,
    val email: String? = null,
    val studentId: Long? = null,
    val password: String? = null
)