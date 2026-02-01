package de.csw.turtle.api.dto.create

data class CreateUserRequest(
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val emojis: String,
    val password: String,
    val roleIds: Set<Long> = setOf(),
    val verified: Boolean = false
) : CreateRequest