package de.csw.turtle.api.dto.get

import de.csw.turtle.api.entity.DeviceCategoryEntity
import java.time.Instant

data class GetDeviceCategoryResponse(
    override val id: Long,
    val name: String,
    override val createdAt: Instant
) : CRUDGetResponse