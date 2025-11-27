package de.csw.turtle.api.dto.get

import de.csw.turtle.api.entity.DeviceEntity
import java.time.Instant

data class GetDeviceResponse(
    override val id: Long,
    val name: String,
    val description: String,
    val inventoryId: String,
    val categoryId: Long,
    val lockerId: Long,
    override val createdAt: Instant
) : CRUDGetResponse