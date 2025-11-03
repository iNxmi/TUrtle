package de.csw.turtle.api.v1.dto.response

data class GetUserResponse(
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val studentId: Long
)