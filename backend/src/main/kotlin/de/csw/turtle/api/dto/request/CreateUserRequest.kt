package de.csw.turtle.api.dto.request

import de.csw.turtle.api.entity.UserEntity
import org.springframework.security.crypto.password.PasswordEncoder

data class CreateUserRequest(
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