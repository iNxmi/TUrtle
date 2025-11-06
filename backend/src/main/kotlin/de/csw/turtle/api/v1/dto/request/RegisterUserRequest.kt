package de.csw.turtle.api.v1.dto.request

data class RegisterUserRequest(
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val studentId: Long,
    val password: String
)