package de.csw.turtle.api.dto.patch

import de.csw.turtle.api.Permission

class PatchRoleRequest(
    val name: String? = null,
    val permissions: Set<Permission>? = null,
) : PatchRequest