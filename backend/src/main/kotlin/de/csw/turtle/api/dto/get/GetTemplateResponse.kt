package de.csw.turtle.api.dto.get

import java.time.Instant

data class GetTemplateResponse(
    override val id: Long?,

    val name: String?,
    val description: String?,
    val content: String?,

    override val createdAt: Instant?
) : GetResponse