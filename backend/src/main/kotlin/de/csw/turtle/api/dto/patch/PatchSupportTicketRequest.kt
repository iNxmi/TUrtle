package de.csw.turtle.api.dto.patch

import de.csw.turtle.api.entity.SupportTicketEntity

data class PatchSupportTicketRequest(
    val status: SupportTicketEntity.Status?
)