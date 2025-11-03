package de.csw.turtle.api.v1.dto.request

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CreateUserRequest(
    @field:NotBlank
    @field:Size(min = 3, max = 32, message = "Username has to be 3 to 32 characters long.")
    val username: String,

    @field:NotBlank
    val firstName: String,

    @field:NotBlank
    val lastName: String,

    @field:NotBlank
    @field:Email
    val email: String,

    val studentId: Long,

    @field:NotBlank
    @field:Size(min = 8, max = 512, message = "Password has to be 8 to 512 characters long.")
    val password: String
)