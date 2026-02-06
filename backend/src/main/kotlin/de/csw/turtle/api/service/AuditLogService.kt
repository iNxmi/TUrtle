package de.csw.turtle.api.service

import de.csw.turtle.api.entity.AuditLogEntity
import de.csw.turtle.api.repository.AuditLogRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class AuditLogService(
    override val repository: AuditLogRepository
) : CRUDService<AuditLogEntity>()