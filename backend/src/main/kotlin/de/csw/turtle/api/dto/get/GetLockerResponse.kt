package de.csw.turtle.api.dto.get

import de.csw.turtle.api.entity.LockerEntity
import java.time.Instant

data class GetLockerResponse(
    override val id: Long?,

    val name: String?,
    val index: Int?,
    val isSoftwareUnlockable: Boolean?,
    val locked: Boolean?,

    override val updatedAt: Instant?,
    override val createdAt: Instant?
) : GetResponse {

    constructor(entity: LockerEntity) : this(
        id = entity.id,

        name = entity.name,
        index = entity.index,
        isSoftwareUnlockable = entity.isSoftwareUnlockable,
        locked = entity.locked,

        updatedAt = entity.updatedAt,
        createdAt = entity.createdAt
    )

}