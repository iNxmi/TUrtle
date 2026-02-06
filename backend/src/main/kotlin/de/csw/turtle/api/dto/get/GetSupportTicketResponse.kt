package de.csw.turtle.api.dto.get

import de.csw.turtle.api.entity.SupportTicketEntity
import java.time.Instant

data class GetSupportTicketResponse(
    override val id: Long?,

    val urgency: SupportTicketEntity.Urgency?,
    val category: SupportTicketEntity.Category?,
    val email: String?,
    val subject: String?,
    val description: String?,
    val status: SupportTicketEntity.Status?,

    override val updatedAt: Instant?,
    override val createdAt: Instant?
) : GetResponse {

    constructor(entity: SupportTicketEntity) : this(
        id = entity.id,

        urgency = entity.urgency,
        category = entity.category,
        email = entity.email,
        subject = entity.subject,
        description = entity.description,
        status = entity.status,

        updatedAt = entity.updatedAt,
        createdAt = entity.createdAt
    )

}