package de.csw.turtle.api.dto.create

import de.csw.turtle.api.entity.AuditLogEntity.HttpMethod

data class CreateAuditLogRequest(
    val userId: Long,
    val ipAddress: String,
    val endpoint: String,
    val httpMethod: HttpMethod
) : CRUDCreateRequest