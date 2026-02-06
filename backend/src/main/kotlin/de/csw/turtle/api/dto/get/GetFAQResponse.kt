package de.csw.turtle.api.dto.get

import de.csw.turtle.api.entity.FAQEntity
import java.time.Instant

class GetFAQResponse(
    override val id: Long?,

    val name: String?,
    val title: String?,
    val content: String?,

    override val updatedAt: Instant?,
    override val createdAt: Instant?
) : GetResponse {

    constructor(entity: FAQEntity) : this(
        id = entity.id,

        name = entity.name,
        title = entity.title,
        content = entity.content,

        updatedAt = entity.updatedAt,
        createdAt = entity.createdAt
    )

}