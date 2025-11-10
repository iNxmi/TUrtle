package de.csw.turtle.api.dto.request

data class UpdateUserRequest(
    val username: String?,
    val firstName: String?,
    val lastName: String?,
    val email: String?,
    val studentId: Long?,
    val password: String?
)
