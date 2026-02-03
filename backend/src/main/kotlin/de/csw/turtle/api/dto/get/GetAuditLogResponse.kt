package de.csw.turtle.api.dto.get

import de.csw.turtle.api.entity.AuditLogEntity
import java.time.Instant

data class GetAuditLogResponse(
    override val id: Long?,

    val ipAddress: String?,
    val userId: Long?,
    val endpoint: String?,
    val status: Int?,
    val httpMethod: AuditLogEntity.HttpMethod?,

    override val updatedAt: Instant?,
    override val createdAt: Instant?
) : GetResponse