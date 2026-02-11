package de.csw.turtle.api.dto.create

import de.csw.turtle.api.entity.UserEntity

data class CreateUserRequest(
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String,
    val emojis: String? = null,
    val status: UserEntity.Status? = null,
    val roleIds: Set<Long>? = null,
    val altchaToken: String? = null
) : CreateRequest