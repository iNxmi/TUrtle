package de.csw.turtle.api.dto.create

import de.csw.turtle.api.entity.RoomBookingEntity
import java.time.Instant

data class CreateRoomBookingRequest(
    val userId: Long? = null,
    val title: String,
    val description: String,
    val start: Instant,
    val end: Instant,
    val access: RoomBookingEntity.Access,
    val whitelistedUserIds: Set<Long>? = null,
    val status: RoomBookingEntity.Status? = null
) : CreateRequest