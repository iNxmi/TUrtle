package de.csw.turtle.api.dto.request

data class LoginUserRequest(
    val username: String,
    val password: String
)