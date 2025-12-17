package de.csw.turtle.api.dto.create

import de.csw.turtle.api.entity.RoomBookingEntity
import java.time.Instant

data class CreateRoomBookingRequest(
    val title: String,
    val description: String,
    val start: Instant,
    val end: Instant,
    val creator: Long,
    val accessibility: RoomBookingEntity.Accessibility? = null,
    val whitelist: Set<Long>? = null
) : CreateRequest