package de.csw.turtle.api.dto.auth

class RegisterUserRequest(
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val altchaToken: String?
)