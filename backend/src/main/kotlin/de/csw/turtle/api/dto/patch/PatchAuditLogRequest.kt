package de.csw.turtle.api.dto.patch

import de.csw.turtle.api.entity.AuditLogEntity.HttpMethod

data class PatchAuditLogRequest(
    val userId: Long? = null,
    val ipAddress: String? = null,
    val endpoint: String? = null,
    val httpMethod: HttpMethod? = null
) : CRUDPatchRequest
