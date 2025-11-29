package de.csw.turtle.api.dto.create

data class CreateRoleRequest(
    val name: String,
    val permissions: Set<String>
) : CRUDCreateRequest