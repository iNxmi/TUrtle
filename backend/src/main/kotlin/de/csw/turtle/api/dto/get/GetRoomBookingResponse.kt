package de.csw.turtle.api.dto.get

import de.csw.turtle.api.entity.RoomBookingEntity
import java.time.Instant

data class GetRoomBookingResponse(
    override val id: Long?,

    val userId: Long?,
    val title: String?,
    val start: Instant?,
    val end: Instant?,
    val description: String?,
    val accessibility: RoomBookingEntity.Accessibility?,
    val whitelist: Set<Long>?,

    override val updatedAt: Instant?,
    override val createdAt: Instant?
) : GetResponse