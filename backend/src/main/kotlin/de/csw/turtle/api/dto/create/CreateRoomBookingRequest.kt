package de.csw.turtle.api.dto.create

import java.time.Instant

data class CreateRoomBookingRequest(
    val title: String,
    val description: String,
    val startTime: Instant,
    val endTime: Instant,
    val creator: Long,
    val whitelist: Set<Long>? = null
) : CRUDCreateRequest