package de.csw.turtle.api.dto.create

import de.csw.turtle.api.entity.SupportTicketEntity

data class CreateSupportTicketRequest(
    val urgency: SupportTicketEntity.Urgency,
    val category: SupportTicketEntity.Category,
    val email: String,
    val subject: String,
    val description: String
) : CreateRequest