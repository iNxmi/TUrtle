package de.csw.turtle.api.dto.auth

import de.csw.turtle.api.entity.UserEntity
import org.springframework.security.crypto.password.PasswordEncoder

data class RegisterUserRequest(
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val altchaToken: String
) {

    //TODO remove create method
    fun create(passwordEncoder: PasswordEncoder) = UserEntity(
        username = username,
        firstName = firstName,
        lastName = lastName,
        email = email,
        emojis = "TODO create random 5 emojis",
        password = passwordEncoder.encode(password)
    )

}