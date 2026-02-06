package de.csw.turtle.api.dto.get

import de.csw.turtle.api.entity.ItemEntity
import java.time.Instant

data class GetItemResponse(
    override val id: Long?,

    val name: String?,
    val description: String?,
    val category: Long?,
    val locker: Long?,
    val acquiredAt: Instant?,

    override val updatedAt: Instant?,
    override val createdAt: Instant?
) : GetResponse {

    constructor(entity: ItemEntity) : this(
        id = entity.id,

        name = entity.name,
        description = entity.description,
        category = entity.category.id,
        locker = entity.locker.id,
        acquiredAt = entity.acquiredAt,

        updatedAt = entity.updatedAt,
        createdAt = entity.createdAt
    )

}