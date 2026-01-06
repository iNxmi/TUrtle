package de.csw.turtle.api.dto.create

import java.time.Instant

class CreateDeviceBookingRequest(
    val start: Instant,
    val end: Instant,
    val device: Long,
    val user: Long
) : CreateRequest