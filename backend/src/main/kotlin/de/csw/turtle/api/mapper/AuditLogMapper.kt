package de.csw.turtle.api.mapper

import de.csw.turtle.api.dto.create.CreateAuditLogRequest
import de.csw.turtle.api.dto.get.GetAuditLogResponse
import de.csw.turtle.api.dto.patch.PatchAuditLogRequest
import de.csw.turtle.api.entity.AuditLogEntity
import de.csw.turtle.api.service.UserService
import org.mapstruct.Mapper
import org.springframework.beans.factory.annotation.Autowired

@Mapper(componentModel = "spring")
abstract class AuditLogMapper :
    CRUDMapper<AuditLogEntity, CreateAuditLogRequest, GetAuditLogResponse, PatchAuditLogRequest> {

    @Autowired
    protected lateinit var userService: UserService

    override fun create(request: CreateAuditLogRequest) = AuditLogEntity(
        user = request.userId?.let { userService.get(it) },
        ipAddress = request.ipAddress,
        endpoint = request.endpoint,
        status = request.status,
        httpMethod = request.httpMethod
    )

    override fun get(entity: AuditLogEntity) = GetAuditLogResponse(
        id = entity.id,
        ipAddress = entity.ipAddress,
        userId = entity.user?.id,
        endpoint = entity.endpoint,
        status = entity.status,
        httpMethod = entity.httpMethod,
        updatedAt = entity.updatedAt,
        createdAt = entity.createdAt
    )

    override fun patch(
        entity: AuditLogEntity,
        request: PatchAuditLogRequest
    ): AuditLogEntity {
        request.userId?.let { entity.user = userService.get(it) }
        request.ipAddress?.let { entity.ipAddress = it }
        request.endpoint?.let { entity.endpoint = it }
        request.status?.let { entity.status = it }
        request.httpMethod?.let { entity.httpMethod = it }
        return entity
    }

}