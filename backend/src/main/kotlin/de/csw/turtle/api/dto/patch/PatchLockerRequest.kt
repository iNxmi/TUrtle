package de.csw.turtle.api.dto.patch

data class PatchLockerRequest(
    val index: Int? = null,
    val name: String? = null
): CRUDPatchRequest