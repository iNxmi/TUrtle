package de.csw.turtle.api.v1.dto.request

data class LoginRequest(
    val username: String,
    val password: String
)