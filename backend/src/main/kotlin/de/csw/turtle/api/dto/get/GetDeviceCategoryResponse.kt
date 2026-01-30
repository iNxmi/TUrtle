package de.csw.turtle.api.dto.get

import java.time.Instant

data class GetDeviceCategoryResponse(
    override val id: Long?,

    val name: String?,

    override val updatedAt: Instant?,
    override val createdAt: Instant?
) : GetResponse