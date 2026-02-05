package de.csw.turtle.api.dto.auth

data class LoginUserRequest(
    val emailOrUsername: String,
    val password: String,
    val rememberMe: Boolean = false,
    val altchaToken: String?
)