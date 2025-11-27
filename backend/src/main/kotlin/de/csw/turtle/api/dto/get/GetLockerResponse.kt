package de.csw.turtle.api.dto.get

import de.csw.turtle.api.entity.LockerEntity
import java.time.Instant

data class GetLockerResponse(
    override val id: Long,
    val index: Int,
    val name: String,
    override val createdAt: Instant
) : CRUDGetResponse