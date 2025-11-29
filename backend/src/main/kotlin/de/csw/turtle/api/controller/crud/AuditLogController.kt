package de.csw.turtle.api.controller.crud

import de.csw.turtle.api.Permission
import de.csw.turtle.api.Permission.*
import de.csw.turtle.api.dto.get.GetAuditLogResponse
import de.csw.turtle.api.entity.AuditLogEntity
import de.csw.turtle.api.mapper.AuditLogMapper
import de.csw.turtle.api.service.AuditLogService
import de.csw.turtle.api.service.SecurityService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/resources/auditlogs")
class AuditLogController(
    override val getPermission: Permission = API_RESOURCES_AUDITLOGS__GET,

    override val service: AuditLogService,
    override val mapper: AuditLogMapper,
    override val securityService: SecurityService,
) : GetController<AuditLogEntity, GetAuditLogResponse>