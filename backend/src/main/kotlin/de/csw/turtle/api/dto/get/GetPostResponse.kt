package de.csw.turtle.api.dto.get

import de.csw.turtle.api.entity.PostEntity
import java.time.Instant

data class GetPostResponse(
    override val id: Long?,

    val name: String?,
    val description: String?,
    val title: String?,
    val content: String?,
    val enabled: Boolean?,

    override val updatedAt: Instant?,
    override val createdAt: Instant?
) : GetResponse {

    constructor(entity: PostEntity) : this(
        id = entity.id,

        name = entity.name,
        description = entity.description,
        title = entity.title,
        content = entity.content,
        enabled = entity.enabled,

        updatedAt = entity.updatedAt,
        createdAt = entity.createdAt
    )

}