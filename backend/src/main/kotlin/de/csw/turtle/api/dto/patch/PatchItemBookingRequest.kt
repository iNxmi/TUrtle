package de.csw.turtle.api.dto.patch

import de.csw.turtle.api.entity.ItemBookingEntity
import java.time.Instant

data class PatchItemBookingRequest(
    val start: Instant? = null,
    val end: Instant? = null,
    val itemId: Long? = null,
    val userId: Long? = null,
    val status: ItemBookingEntity.Status? = null
) : PatchRequest