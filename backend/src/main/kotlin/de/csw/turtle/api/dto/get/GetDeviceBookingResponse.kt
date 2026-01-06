package de.csw.turtle.api.dto.get

import java.time.Instant

class GetDeviceBookingResponse(
    override val id: Long,
    val start: Instant,
    val end: Instant,
    val device: Long,
    val user: Long,
    override val createdAt: Instant
) : GetResponse