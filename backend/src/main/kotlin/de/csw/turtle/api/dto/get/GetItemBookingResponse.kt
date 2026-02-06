package de.csw.turtle.api.dto.get

import de.csw.turtle.api.entity.ItemBookingEntity
import java.time.Instant

class GetItemBookingResponse(
    override val id: Long,

    val userId: Long,
    val itemId: Long,
    val start: Instant,
    val end: Instant,
    val status : ItemBookingEntity.Status,

    override val updatedAt: Instant?,
    override val createdAt: Instant
) : GetResponse {

}