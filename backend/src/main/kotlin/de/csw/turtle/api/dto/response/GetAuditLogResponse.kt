package de.csw.turtle.api.dto.response

import de.csw.turtle.api.entity.AuditLogEntity
import java.time.Instant

data class GetAuditLogResponse(
    val id: Long,
    val ipAddress: String,
    val username: String,
    val endpoint: String,
    val httpMethod: AuditLogEntity.HttpMethod,
    val createdAt: Instant
) {
    constructor(entity: AuditLogEntity) : this(
        id = entity.id,
        ipAddress = entity.ipAddress,
        username = entity.user.username,
        endpoint = entity.endpoint,
        httpMethod = entity.httpMethod,
        createdAt = entity.createdAt
    )
}