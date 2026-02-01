package de.csw.turtle.api.dto.get

import java.time.Instant

data class GetGeneralTemplateResponse(
    override val id: Long?,

    val name: String?,
    val description: String?,
    val content: String?,

    override val updatedAt: Instant?,
    override val createdAt: Instant?
) : GetResponse