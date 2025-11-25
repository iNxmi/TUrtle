package de.csw.turtle.api.dto

data class LoginUserRequest(
    val username: String,
    val password: String,
    val rememberMe: Boolean = false
)