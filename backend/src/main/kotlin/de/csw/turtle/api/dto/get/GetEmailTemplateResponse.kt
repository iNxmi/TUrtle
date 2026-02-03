package de.csw.turtle.api.dto.get

import java.time.Instant

data class GetEmailTemplateResponse(
    override val id: Long?,

    val name: String?,
    val description: String?,
    val subject: String?,
    val content: String?,

    override val updatedAt: Instant?,
    override val createdAt: Instant?
) : GetResponse