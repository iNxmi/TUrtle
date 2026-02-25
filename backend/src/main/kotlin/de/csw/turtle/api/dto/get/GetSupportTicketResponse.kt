package de.csw.turtle.api.dto.get

import de.csw.turtle.api.entity.SupportTicketEntity
import java.time.Instant

data class GetSupportTicketResponse(
    override val id: Long?,

    val urgencyId: Long?,
    val categoryId: Long?,
    val email: String?,
    val subject: String?,
    val content: String?,
    val status: SupportTicketEntity.Status?,

    override val updatedAt: Instant?,
    override val createdAt: Instant?
) : GetResponse {

    constructor(entity: SupportTicketEntity) : this(
        id = entity.id,

        urgencyId = entity.urgency.id,
        categoryId = entity.category.id,
        email = entity.email,
        subject = entity.subject,
        content = entity.content,
        status = entity.status,

        updatedAt = entity.updatedAt,
        createdAt = entity.createdAt
    )

}