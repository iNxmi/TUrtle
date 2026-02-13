package de.csw.turtle.api.service

import de.csw.turtle.api.entity.AuditLogEntity
import de.csw.turtle.api.repository.AuditLogRepository
import de.csw.turtle.api.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class AuditLogService(
    override val repository: AuditLogRepository,
    private val userRepository: UserRepository
) : CRUDService<AuditLogEntity>() {

    @Transactional
    fun create(
        userId: Long?,
        ipAddress: String,
        endpoint: String,
        status: Int,
        httpMethod: AuditLogEntity.HttpMethod
    ): AuditLogEntity {
        val entity = AuditLogEntity(
            user = userId?.let { userRepository.findById(it).get() },
            ipAddress = ipAddress,
            endpoint = endpoint,
            status = status,
            httpMethod = httpMethod
        )
        return repository.save(entity)
    }

}