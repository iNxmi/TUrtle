package de.csw.turtle.api.dto.create

import de.csw.turtle.api.entity.ItemBookingEntity
import java.time.Instant

data class CreateItemBookingRequest(
    val start: Instant,
    val end: Instant,
    val itemId: Long,
    val userId: Long,
    val status: ItemBookingEntity.Status = ItemBookingEntity.Status.RESERVED
) : CreateRequest