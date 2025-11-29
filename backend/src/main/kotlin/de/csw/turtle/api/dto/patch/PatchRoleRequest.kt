package de.csw.turtle.api.dto.patch

class PatchRoleRequest(
    val name: String? = null,
    val permissions: Set<String>? = null,
) : CRUDPatchRequest