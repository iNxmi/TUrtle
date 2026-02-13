package de.csw.turtle.api.dto.create

import de.csw.turtle.api.Permission
import de.csw.turtle.api.entity.RoleEntity.Type

data class CreateRoleRequest(
    val name: String,
    val permissions: Set<Permission>,
    val type: Type? = null
) : CreateRequest