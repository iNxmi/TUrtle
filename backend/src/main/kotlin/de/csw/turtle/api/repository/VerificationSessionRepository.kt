package de.csw.turtle.api.repository

import de.csw.turtle.api.entity.VerificationSessionEntity
import java.util.UUID

interface VerificationSessionRepository : CRUDRepository<VerificationSessionEntity> {

    fun findByUuid(uuid: UUID): VerificationSessionEntity?

}