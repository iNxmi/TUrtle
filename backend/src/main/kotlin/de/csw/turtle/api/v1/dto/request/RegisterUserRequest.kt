package de.csw.turtle.api.v1.dto.request

import de.csw.turtle.api.v1.entity.UserEntity
import org.springframework.security.crypto.password.PasswordEncoder


data class RegisterUserRequest(
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val studentId: Long,
    val password: String
) {
    fun create(passwordEncoder: PasswordEncoder) = UserEntity(
        username,
        firstName,
        lastName,
        email,
        studentId,
        passwordEncoder.encode(password)
    )
}