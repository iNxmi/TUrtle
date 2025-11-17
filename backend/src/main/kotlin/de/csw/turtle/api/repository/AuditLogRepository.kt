package de.csw.turtle.api.repository

import de.csw.turtle.api.entity.AuditLogEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AuditLogRepository : JpaRepository<AuditLogEntity, Long>