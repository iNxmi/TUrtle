package de.csw.turtle.api.v1.dto.request

data class LoginUserRequest(
    val username: String,
    val password: String
)