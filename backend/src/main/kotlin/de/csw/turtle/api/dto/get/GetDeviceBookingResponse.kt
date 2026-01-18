package de.csw.turtle.api.dto.get

import de.csw.turtle.api.entity.DeviceBookingEntity
import java.time.Instant

class GetDeviceBookingResponse(
    override val id: Long?,
    val start: Instant?,
    val end: Instant?,
    val deviceId: Long?,
    val userId: Long?,
    val status : DeviceBookingEntity.Status?,
    override val createdAt: Instant?
) : GetResponse