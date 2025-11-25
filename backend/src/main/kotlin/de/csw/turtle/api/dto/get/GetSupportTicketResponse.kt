package de.csw.turtle.api.dto.get

import de.csw.turtle.api.entity.SupportTicketEntity
import java.time.Instant

data class GetSupportTicketResponse(
    val id: Long,
    val urgency: SupportTicketEntity.Urgency,
    val category: SupportTicketEntity.Category,
    val email: String,
    val subject: String,
    val description: String,
    val createdAt: Instant
) {

    constructor(entity: SupportTicketEntity) : this(
        id = entity.id,
        urgency = entity.urgency,
        category = entity.category,
        email = entity.email,
        subject = entity.subject,
        description = entity.description,
        createdAt = entity.createdAt
    )

}