package de.csw.turtle.api.dto.get

import de.csw.turtle.api.entity.DeviceBookingEntity
import java.time.Instant

class GetDeviceBookingResponse(
    override val id: Long,

    val userId: Long,
    val deviceId: Long,
    val start: Instant,
    val end: Instant,
    val status : DeviceBookingEntity.Status,

    override val updatedAt: Instant?,
    override val createdAt: Instant
) : GetResponse