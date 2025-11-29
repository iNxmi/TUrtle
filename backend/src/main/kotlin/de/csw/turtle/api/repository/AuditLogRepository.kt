package de.csw.turtle.api.repository

import de.csw.turtle.api.entity.AuditLogEntity
import de.csw.turtle.api.entity.UserEntity

interface AuditLogRepository : CRUDRepository<AuditLogEntity> {

    fun findAllByUser(user: UserEntity): Collection<AuditLogEntity>

}