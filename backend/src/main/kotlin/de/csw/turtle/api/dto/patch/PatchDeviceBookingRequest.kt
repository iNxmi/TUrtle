package de.csw.turtle.api.dto.patch

import de.csw.turtle.api.entity.DeviceBookingEntity
import java.time.Instant

data class PatchDeviceBookingRequest(
    val start: Instant? = null,
    val end: Instant? = null,
    val deviceId: Long? = null,
    val userId: Long? = null,
    val status: DeviceBookingEntity.Status? = null
) : PatchRequest