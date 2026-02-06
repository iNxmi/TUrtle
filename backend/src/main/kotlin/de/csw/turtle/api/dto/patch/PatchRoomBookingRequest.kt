package de.csw.turtle.api.dto.patch

import de.csw.turtle.api.entity.RoomBookingEntity
import java.time.Instant

data class PatchRoomBookingRequest(
    val userId: Long? = null,
    val title: String? = null,
    val start: Instant? = null,
    val end: Instant? = null,
    val description: String? = null,
    val accessibility: RoomBookingEntity.Accessibility? = null,
    val whitelist: Set<Long>? = null,
    val status: RoomBookingEntity.Status? = null
) : PatchRequest