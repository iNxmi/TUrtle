package de.csw.turtle.api.dto.patch

import de.csw.turtle.api.entity.SupportTicketEntity

data class PatchSupportTicketRequest(
    val urgency: SupportTicketEntity.Urgency? = null,
    val category: SupportTicketEntity.Category? = null,
    val email: String? = null,
    val subject: String? = null,
    val description: String? = null,
    val status: SupportTicketEntity.Status? = null
) : PatchRequest