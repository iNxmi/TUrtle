package de.csw.turtle.api.dto.get

import de.csw.turtle.api.entity.ItemEntity
import java.time.Instant

data class GetItemResponse(
    override val id: Long?,

    val name: String?,
    val description: String?,
    val categoryId: Long?,
    val lockerId: Long?,
    val needsConfirmation: Boolean?,
    val acquiredAt: Instant?,

    override val updatedAt: Instant?,
    override val createdAt: Instant?
) : GetResponse {

    constructor(entity: ItemEntity) : this(
        id = entity.id,

        name = entity.name,
        description = entity.description,
        categoryId = entity.category.id,
        lockerId = entity.locker.id,
        needsConfirmation = entity.needsConfirmation,
        acquiredAt = entity.acquiredAt,

        updatedAt = entity.updatedAt,
        createdAt = entity.createdAt
    )

}