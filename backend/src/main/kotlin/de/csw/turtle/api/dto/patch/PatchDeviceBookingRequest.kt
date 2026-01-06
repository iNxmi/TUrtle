package de.csw.turtle.api.dto.patch

import java.time.Instant

class PatchDeviceBookingRequest(
    val start: Instant? = null,
    val end: Instant? = null,
    val device: Long? = null,
    val user: Long? = null
) : PatchRequest