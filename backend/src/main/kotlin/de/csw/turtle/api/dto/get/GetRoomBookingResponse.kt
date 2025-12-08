package de.csw.turtle.api.dto.get

import java.time.Instant

data class GetRoomBookingResponse(
    override val id: Long,
    val title: String,
    val startTime: Instant,
    val endTime: Instant,
    val description: String,
    val creator: Long,
    val whitelist: Set<Long>?,
    override val createdAt: Instant
) : CRUDGetResponse