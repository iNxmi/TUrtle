package de.csw.turtle.api.dto.get

import java.time.Instant

data class GetLockerResponse(
    override val id: Long?,

    val name: String?,
    val index: Int?,
    val isSoftwareUnlockable: Boolean?,
    val locked: Boolean?,

    override val updatedAt: Instant?,
    override val createdAt: Instant?
) : GetResponse