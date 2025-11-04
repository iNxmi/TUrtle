package de.csw.turtle.api.v1.dto.request

import de.csw.turtle.api.v1.entity.UserEntity
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateUserRequest(
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val studentId: Long,
    val password: String
) {

    fun create() = UserEntity (
        username = username,
        firstName = firstName,
        lastName = lastName,
        email = email,
        studentId = studentId,
        passwordHash = password // TODO hash the password
    )

}