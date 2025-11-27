package de.csw.turtle.api.controller.crud

import de.csw.turtle.api.dto.create.CreateAuditLogRequest
import de.csw.turtle.api.dto.create.CreateUserRequest
import de.csw.turtle.api.dto.get.GetAuditLogResponse
import de.csw.turtle.api.dto.get.GetUserResponse
import de.csw.turtle.api.dto.patch.PatchAuditLogRequest
import de.csw.turtle.api.dto.patch.PatchUserRequest
import de.csw.turtle.api.entity.AuditLogEntity
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.exceptions.exception.ExceptionNotFoundException
import de.csw.turtle.api.mapper.AuditLogMapper
import de.csw.turtle.api.mapper.UserMapper
import de.csw.turtle.api.repository.AuditLogRepository
import de.csw.turtle.api.repository.UserRepository
import de.csw.turtle.api.service.AuditLogService
import de.csw.turtle.api.service.UserService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort.Direction
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import kotlin.jvm.optionals.getOrNull

@RestController
@RequestMapping("/api/auditlogs")
class AuditLogController(
    override val service: AuditLogService,
    override val mapper: AuditLogMapper
) : CRUDController<AuditLogEntity, CreateAuditLogRequest, GetAuditLogResponse, PatchAuditLogRequest, AuditLogRepository, AuditLogMapper, AuditLogService>(
    "/api/auditlogs",
    "api.auditlogs"
)