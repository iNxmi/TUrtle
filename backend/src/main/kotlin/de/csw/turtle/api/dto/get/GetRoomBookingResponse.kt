package de.csw.turtle.api.dto.get

import java.time.Instant

data class GetRoomBookingResponse(
    override val id: Long,
    val title: String,
    val start: Instant,
    val end: Instant,
    val description: String,
    val creator: Long,
    val enableWhitelist: Boolean,
    val whitelist: Set<Long>,
    override val createdAt: Instant
) : CRUDGetResponse