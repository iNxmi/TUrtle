package de.csw.turtle.api.dto.get

import de.csw.turtle.api.entity.SupportTicketCategoryEntity
import de.csw.turtle.api.entity.SupportTicketUrgencyEntity
import java.time.Instant

data class GetSupportTicketUrgencyResponse(
    override val id: Long?,

    val name: String?,

    override val updatedAt: Instant?,
    override val createdAt: Instant?
) : GetResponse {

    constructor(entity: SupportTicketUrgencyEntity) : this(
        id = entity.id,

        name = entity.name,

        updatedAt = entity.updatedAt,
        createdAt = entity.createdAt
    )

}