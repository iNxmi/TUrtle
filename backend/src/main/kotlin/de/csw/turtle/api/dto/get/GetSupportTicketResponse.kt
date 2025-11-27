package de.csw.turtle.api.dto.get

import de.csw.turtle.api.entity.SupportTicketEntity
import java.time.Instant

data class GetSupportTicketResponse(
    override val id: Long,
    val urgency: SupportTicketEntity.Urgency,
    val category: SupportTicketEntity.Category,
    val email: String,
    val subject: String,
    val description: String,
    val status: SupportTicketEntity.Status,
    override val createdAt: Instant
) : CRUDGetResponse