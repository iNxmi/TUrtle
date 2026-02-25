package de.csw.turtle.api.dto.patch

import de.csw.turtle.api.entity.SupportTicketEntity

data class PatchSupportTicketRequest(
    val urgencyId: Long? = null,
    val categoryId: Long? = null,
    val email: String? = null,
    val subject: String? = null,
    val content: String? = null,
    val status: SupportTicketEntity.Status? = null
) : PatchRequest