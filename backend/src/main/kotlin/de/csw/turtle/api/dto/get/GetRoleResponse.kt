package de.csw.turtle.api.dto.get

import de.csw.turtle.api.Permission
import de.csw.turtle.api.entity.RoleEntity
import de.csw.turtle.api.entity.RoleEntity.Type
import java.time.Instant

data class GetRoleResponse(
    override val id: Long?,

    val name: String?,
    val permissions: Set<Permission>?,
    val type: Type?,

    override val updatedAt: Instant?,
    override val createdAt: Instant?
) : GetResponse {

    constructor(entity: RoleEntity) : this(
        id = entity.id,

        name = entity.name,
        permissions = entity.permissions,
        type = entity.type,

        updatedAt = entity.updatedAt,
        createdAt = entity.createdAt
    )

}