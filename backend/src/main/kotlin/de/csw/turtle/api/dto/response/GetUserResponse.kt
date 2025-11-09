package de.csw.turtle.api.dto.response

import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.entity.UserEntity.Role
import java.time.Instant

data class GetUserResponse(
    val userName: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val studentId: Long,
    val role: Role,
    val createdAt: Instant
) {

    constructor(user: UserEntity) : this(
        userName = user.userName,
        firstName = user.firstName,
        lastName = user.lastName,
        email = user.email,
        studentId = user.studentId,
        role = user.role,
        createdAt = user.createdAt
    )

}