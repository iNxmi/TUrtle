package de.csw.turtle.api.dto.request

class PatchProfileRequest(
    firstName: String? = null,
    lastName: String? = null,
    email: String? = null,
    studentId: Long? = null,
    password: String? = null
) : PatchUserRequest(
    firstName = firstName,
    lastName = lastName,
    email = email,
    studentId = studentId,
    password = password
)