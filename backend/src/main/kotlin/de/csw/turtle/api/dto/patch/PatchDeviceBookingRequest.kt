package de.csw.turtle.api.dto.patch

import java.time.Instant

class PatchDeviceBookingRequest(
    val start: Instant? = null,
    val end: Instant? = null,
    val deviceId: Long? = null,
    val userId: Long? = null
) : PatchRequest