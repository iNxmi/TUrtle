package de.csw.turtle.api.dto.get

import de.csw.turtle.api.entity.StatisticQueryEntity
import java.time.Instant

data class GetStatisticQueryResponse(
    override val id: Long?,

    val name: String?,
    val description: String?,
    val query: String?,
    val type: StatisticQueryEntity.Type?,

    override val updatedAt: Instant?,
    override val createdAt: Instant?
) : GetResponse {

    constructor(entity: StatisticQueryEntity) : this(
        id = entity.id,
        name = entity.name,
        description = entity.description,
        query = entity.query,
        type = entity.type,
        updatedAt = entity.updatedAt,
        createdAt = entity.createdAt
    )

}