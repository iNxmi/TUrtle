package de.csw.turtle.api.dto.get

import de.csw.turtle.api.entity.UserEntity
import java.time.Instant

data class GetUserResponse(
    override val id: Long?,

    val username: String?,
    val firstName: String?,
    val lastName: String?,
    val email: String?,
    val emojis: String,
    val roleIds: Set<Long>?,
    val status: UserEntity.Status?,

    override val updatedAt: Instant?,
    override val createdAt: Instant?
) : GetResponse {

    constructor(user: UserEntity) : this(
        id = user.id,

        username = user.username,
        firstName = user.firstName,
        lastName = user.lastName,
        email = user.email,
        emojis = user.emojis,
        roleIds = user.roles.map { it.id }.toSortedSet(),
        status = user.status,

        updatedAt = user.updatedAt,
        createdAt = user.createdAt
    )

}