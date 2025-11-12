package de.csw.turtle.api.dto.response

import de.csw.turtle.api.entity.SupportTicketEntity

data class GetSupportTicketResponse(
    val urgency: SupportTicketEntity.Urgency,
    val category: SupportTicketEntity.Category,
    val email: String,
    val subject: String,
    val description: String
) {

    constructor(entity: SupportTicketEntity) : this(
        urgency = entity.urgency,
        category = entity.category,
        email = entity.email,
        subject = entity.subject,
        description = entity.description
    )

}