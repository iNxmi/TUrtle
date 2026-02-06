package de.csw.turtle.api.dto.create

import de.csw.turtle.api.entity.RoomBookingEntity
import java.time.Instant

data class CreateRoomBookingRequest(
    val userId: Long,
    val title: String,
    val description: String,
    val start: Instant,
    val end: Instant,
    val accessibility: RoomBookingEntity.Accessibility,
    val whitelist: Set<Long>? = null
) : CreateRequest