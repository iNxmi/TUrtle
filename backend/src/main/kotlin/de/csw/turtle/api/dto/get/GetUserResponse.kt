package de.csw.turtle.api.dto.get

import de.csw.turtle.api.Permission
import java.time.Instant

data class GetUserResponse(
    override val id: Long,
    val username: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val roles: Set<GetRoleResponse>,
    val permissions: Set<Permission>,
    override val createdAt: Instant
) : CRUDGetResponse {

    data class GetRoleResponse(
        val id: Long,
        val name: String
    )

}