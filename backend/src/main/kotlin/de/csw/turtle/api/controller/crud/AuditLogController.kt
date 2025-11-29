package de.csw.turtle.api.controller.crud

import de.csw.turtle.api.dto.create.CreateAuditLogRequest
import de.csw.turtle.api.dto.get.GetAuditLogResponse
import de.csw.turtle.api.dto.patch.PatchAuditLogRequest
import de.csw.turtle.api.entity.AuditLogEntity
import de.csw.turtle.api.mapper.AuditLogMapper
import de.csw.turtle.api.repository.AuditLogRepository
import de.csw.turtle.api.service.AuditLogService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/auditlogs")
class AuditLogController(
    override val service: AuditLogService,
    override val mapper: AuditLogMapper
) : CRUDController<AuditLogEntity, CreateAuditLogRequest, GetAuditLogResponse, PatchAuditLogRequest, AuditLogRepository, AuditLogMapper, AuditLogService>(
    "/api/auditlogs",
    "api.auditlogs"
)