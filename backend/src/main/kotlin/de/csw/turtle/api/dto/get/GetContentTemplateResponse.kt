package de.csw.turtle.api.dto.get

import de.csw.turtle.api.entity.ContentTemplateEntity
import java.time.Instant

data class GetContentTemplateResponse(
    override val id: Long?,

    val name: String?,
    val description: String?,
    val content: String?,
    val type: ContentTemplateEntity.Type?,

    override val updatedAt: Instant?,
    override val createdAt: Instant?
) : GetResponse {

    constructor(entity: ContentTemplateEntity) : this(
        id = entity.id,

        name = entity.name,
        description = entity.description,
        content = entity.content,
        type = entity.type,

        updatedAt = entity.updatedAt,
        createdAt = entity.createdAt
    )

}