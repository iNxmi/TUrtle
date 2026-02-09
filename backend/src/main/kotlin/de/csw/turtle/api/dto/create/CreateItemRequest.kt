package de.csw.turtle.api.dto.create

import java.time.Instant

data class CreateItemRequest(
    val name: String,
    val description: String,
    val categoryId: Long,
    val lockerId: Long,
    val needsConfirmation: Boolean,
    val acquiredAt: Instant
) : CreateRequest