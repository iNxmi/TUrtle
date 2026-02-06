package de.csw.turtle.api.dto.get

import de.csw.turtle.api.entity.GeneralTemplateEntity
import java.time.Instant

data class GetGeneralTemplateResponse(
    override val id: Long?,

    val name: String?,
    val description: String?,
    val content: String?,

    override val updatedAt: Instant?,
    override val createdAt: Instant?
) : GetResponse {

    constructor(entity: GeneralTemplateEntity) : this(
        id = entity.id,

        name = entity.name,
        description = entity.description,
        content = entity.content,

        updatedAt = entity.updatedAt,
        createdAt = entity.createdAt
    )

}