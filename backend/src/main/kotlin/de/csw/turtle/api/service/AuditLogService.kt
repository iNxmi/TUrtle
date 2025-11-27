package de.csw.turtle.api.service

import de.csw.turtle.api.dto.create.CreateAuditLogRequest
import de.csw.turtle.api.dto.get.GetAuditLogResponse
import de.csw.turtle.api.dto.patch.PatchAuditLogRequest
import de.csw.turtle.api.entity.AuditLogEntity
import de.csw.turtle.api.mapper.AuditLogMapper
import de.csw.turtle.api.repository.AuditLogRepository
import org.springframework.stereotype.Service

@Service
class AuditLogService(
    override val repository: AuditLogRepository,
    override val mapper: AuditLogMapper
) : CRUDService<AuditLogEntity, CreateAuditLogRequest, GetAuditLogResponse, PatchAuditLogRequest, AuditLogRepository, AuditLogMapper>()