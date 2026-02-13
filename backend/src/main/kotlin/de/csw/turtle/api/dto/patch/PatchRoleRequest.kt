package de.csw.turtle.api.dto.patch

import de.csw.turtle.api.Permission
import de.csw.turtle.api.entity.RoleEntity.Type

class PatchRoleRequest(
    val name: String? = null,
    val permissions: Set<Permission>? = null,
    val type: Type? = null
) : PatchRequest