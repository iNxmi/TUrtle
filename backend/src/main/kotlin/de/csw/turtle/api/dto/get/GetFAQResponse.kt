package de.csw.turtle.api.dto.get

import java.time.Instant

class GetFAQResponse(
    override val id: Long,
    val name: String,
    val title: String,
    val content: String,
    override val createdAt: Instant
) : CRUDGetResponse