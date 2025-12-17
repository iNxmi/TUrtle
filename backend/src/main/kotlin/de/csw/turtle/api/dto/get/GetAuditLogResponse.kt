package de.csw.turtle.api.dto.get

import de.csw.turtle.api.entity.AuditLogEntity
import java.time.Instant

data class GetAuditLogResponse(
    override val id: Long,

    val ipAddress: String?,
    val username: String?,
    val endpoint: String?,
    val httpMethod: AuditLogEntity.HttpMethod?,

    override val createdAt: Instant
) : GetResponse