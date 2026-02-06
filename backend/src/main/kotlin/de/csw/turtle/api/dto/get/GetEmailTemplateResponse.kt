package de.csw.turtle.api.dto.get

import de.csw.turtle.api.entity.EmailTemplateEntity
import java.time.Instant

data class GetEmailTemplateResponse(
    override val id: Long?,

    val name: String?,
    val description: String?,
    val subject: String?,
    val content: String?,

    override val updatedAt: Instant?,
    override val createdAt: Instant?
) : GetResponse {

    constructor(entity: EmailTemplateEntity) : this(
        id = entity.id,

        name = entity.name,
        description = entity.description,
        subject = entity.subject,
        content = entity.content,

        updatedAt = entity.updatedAt,
        createdAt = entity.createdAt
    )

}