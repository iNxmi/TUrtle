package de.csw.turtle.api.controller.api

import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.dto.get.GetAuditLogResponse
import de.csw.turtle.api.entity.AuditLogEntity
import de.csw.turtle.api.mapper.AuditLogMapper
import de.csw.turtle.api.service.AuditLogService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/audit-logs")
class AuditLogController(
    override val service: AuditLogService,
    override val mapper: AuditLogMapper
) : GetController<AuditLogEntity, GetAuditLogResponse>