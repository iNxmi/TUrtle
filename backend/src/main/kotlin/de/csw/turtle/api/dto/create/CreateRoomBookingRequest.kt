package de.csw.turtle.api.dto.create

import java.time.Instant

data class CreateRoomBookingRequest(
    val title: String,
    val description: String,
    val start: Instant,
    val end: Instant,
    val creator: Long,
    val enableWhitelist: Boolean,
    val whitelist: Set<Long>? = null
) : CRUDCreateRequest