package de.csw.turtle.api.dto.auth

data class ResetUserPasswordRequest(
    val token: String,
    val password: String
)