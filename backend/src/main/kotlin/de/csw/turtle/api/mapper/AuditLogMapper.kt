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
        user = userService.get(request.userId),
        ipAddress = request.ipAddress,
        endpoint = request.endpoint,
        httpMethod = request.httpMethod
    )

    override fun get(entity: AuditLogEntity) = GetAuditLogResponse(
        id = entity.id,
        ipAddress = entity.ipAddress,
        username = entity.user.username,
        endpoint = entity.endpoint,
        httpMethod = entity.httpMethod,
        createdAt = entity.createdAt
    )

    override fun patch(
        entity: AuditLogEntity,
        request: PatchAuditLogRequest
    ): AuditLogEntity {
        request.userId?.let { entity.user = userService.get(it) }
        request.ipAddress?.let { entity.ipAddress = it }
        request.endpoint?.let { entity.endpoint = it }
        request.httpMethod?.let { entity.httpMethod = it }
        return entity
    }

}