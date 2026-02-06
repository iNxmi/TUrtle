package de.csw.turtle.api.dto.get

import de.csw.turtle.api.Permission
import de.csw.turtle.api.entity.RoleEntity
import java.time.Instant

data class GetRoleResponse(
    override val id: Long?,

    val name: String?,
    val permissions: Set<Permission>?,

    override val updatedAt: Instant?,
    override val createdAt: Instant?
) : GetResponse {

    constructor(entity: RoleEntity) : this(
        id = entity.id,

        name = entity.name,
        permissions = entity.permissions,

        updatedAt = entity.updatedAt,
        createdAt = entity.createdAt
    )

}