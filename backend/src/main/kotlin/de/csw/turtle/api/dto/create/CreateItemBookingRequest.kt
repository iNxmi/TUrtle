package de.csw.turtle.api.dto.create

import de.csw.turtle.api.entity.ItemBookingEntity
import java.time.Instant

data class CreateItemBookingRequest(
    val start: Instant,
    val end: Instant,
    val itemId: Long,
    val userId: Long? = null,
    val collectedAt: Instant? = null,
    val returnedAt: Instant? = null,
    val status: ItemBookingEntity.Status? = null
) : CreateRequest