package de.csw.turtle.api.dto.patch

data class PatchLockerRequest(
    val index: Int? = null,
    val name: String? = null,
    val isSoftwareUnlockable: Boolean? = null,
    val locked: Boolean? = null
) : PatchRequest