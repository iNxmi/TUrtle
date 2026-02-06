package de.csw.turtle.api.dto.get

import de.csw.turtle.api.entity.ItemCategoryEntity
import java.time.Instant

data class GetItemCategoryResponse(
    override val id: Long?,

    val name: String?,

    override val updatedAt: Instant?,
    override val createdAt: Instant?
) : GetResponse {

    constructor(entity: ItemCategoryEntity) : this(
        id = entity.id,

        name = entity.name,

        updatedAt = entity.updatedAt,
        createdAt = entity.createdAt
    )

}