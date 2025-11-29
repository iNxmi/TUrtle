package de.csw.turtle.api.dto.get

import java.time.Instant

data class GetDeviceResponse(
    override val id: Long,
    val name: String,
    val description: String,
    val inventoryId: String,
    val category: Long,
    val locker: Long,
    override val createdAt: Instant
) : CRUDGetResponse