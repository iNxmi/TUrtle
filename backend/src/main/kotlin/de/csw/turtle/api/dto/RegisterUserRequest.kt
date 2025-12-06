package de.csw.turtle.api.dto

import de.csw.turtle.api.entity.UserEntity
import org.springframework.security.crypto.password.PasswordEncoder

data class RegisterUserRequest(
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
) {

    //TODO remove create method
    fun create(passwordEncoder: PasswordEncoder) = UserEntity(
        username = username,
        firstName = firstName,
        lastName = lastName,
        email = email,
        password = passwordEncoder.encode(password)
    )

}