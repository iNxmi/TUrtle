package de.csw.turtle.api.dto.create

import de.csw.turtle.api.Permission

data class CreateRoleRequest(
    val name: String,
    val permissions: Set<Permission>
) : CreateRequest