package de.csw.turtle.api.dto.patch

import java.time.Instant

data class PatchItemRequest(
    val name: String? = null,
    val description: String? = null,
    val categoryId: Long? = null,
    val lockerId: Long? = null,
    val acquiredAt: Instant? = null
) : PatchRequest