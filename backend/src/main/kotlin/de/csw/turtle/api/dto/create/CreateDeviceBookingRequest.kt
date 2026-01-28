package de.csw.turtle.api.dto.create

import de.csw.turtle.api.entity.DeviceBookingEntity
import java.time.Instant

data class CreateDeviceBookingRequest(
    val start: Instant,
    val end: Instant,
    val deviceId: Long,
    val userId: Long,
    val status: DeviceBookingEntity.Status = DeviceBookingEntity.Status.RESERVED
) : CreateRequest