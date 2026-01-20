package de.csw.turtle.api.dto.create

data class CreateLockerRequest(
    val index: Int,
    val name: String,
    val isSoftwareUnlockable: Boolean,
    val locked: Boolean = false
) : CreateRequest