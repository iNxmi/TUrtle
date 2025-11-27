package de.csw.turtle.api.repository

import de.csw.turtle.api.entity.AuditLogEntity
import de.csw.turtle.api.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface AuditLogRepository : CRUDRepository<AuditLogEntity>{

    fun findAllByUser(user: UserEntity): Collection<AuditLogEntity>

}