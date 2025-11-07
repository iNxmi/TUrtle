package de.csw.turtle.api.v1.dto.response

import de.csw.turtle.api.v1.entity.UserEntity

data class GetUserResponse(
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val studentId: Long
) {

    constructor(user: UserEntity) : this(
        username = user.userName,
        firstName = user.firstName,
        lastName = user.lastName,
        email = user.email,
        studentId = user.studentId
    )

}