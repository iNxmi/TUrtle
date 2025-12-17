package de.csw.turtle.api.dto.get

import java.time.Instant

data class GetLockerResponse(
    override val id: Long?,

    val name: String?,
    val index: Int?,
    val isSoftwareUnlockable: Boolean?,

    override val createdAt: Instant?
) : GetResponse