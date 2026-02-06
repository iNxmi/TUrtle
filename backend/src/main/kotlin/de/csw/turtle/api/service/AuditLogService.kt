package de.csw.turtle.api.service

import de.csw.turtle.api.entity.AuditLogEntity
import de.csw.turtle.api.repository.AuditLogRepository
import org.springframework.stereotype.Service

@Service
class AuditLogService(
    override val repository: AuditLogRepository
) : CRUDService<AuditLogEntity>("Audit Log")