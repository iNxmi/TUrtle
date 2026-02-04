package de.csw.turtle.api.dto

data class LoginUserRequest(
    val emailOrUsername: String,
    val password: String,
    val rememberMe: Boolean = false
)