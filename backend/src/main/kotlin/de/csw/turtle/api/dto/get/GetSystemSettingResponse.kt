package de.csw.turtle.api.dto.get

import java.time.Instant

class GetSystemSettingResponse(
    override val id: Long,
    val key: String,
    val value: String,
    override val createdAt: Instant
) : GetResponse