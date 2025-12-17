package de.csw.turtle.api.dto.create

import java.time.Instant

data class CreateDeviceRequest(
    val name: String,
    val description: String,
    val categoryId: Long,
    val lockerId: Long,
    val acquiredAt: Instant
) : CreateRequest