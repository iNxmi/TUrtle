package de.csw.turtle.api.dto.auth

data class ResetUserPasswordRequest(
    val session: String,
    val password: String
)