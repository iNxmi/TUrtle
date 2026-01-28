package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Permission
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.dto.get.GetAuditLogResponse
import de.csw.turtle.api.entity.AuditLogEntity
import de.csw.turtle.api.exception.ForbiddenException
import de.csw.turtle.api.exception.UnauthorizedException
import de.csw.turtle.api.mapper.AuditLogMapper
import de.csw.turtle.api.service.AuditLogService
import org.springframework.data.domain.Sort.Direction
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/audit-logs")
class AuditLogController(
    private val auditLogService: AuditLogService,
    private val auditLogMapper: AuditLogMapper
) :
    GetController<AuditLogEntity, GetAuditLogResponse> {

    override fun get(
        user: UserEntity?,
        id: Long
    ): ResponseEntity<GetAuditLogResponse> {
        if (user == null)
            throw UnauthorizedException()

        if (!user.hasPermission(Permission.MANAGE_AUDIT_LOGS))
            throw ForbiddenException()

        val entity = auditLogService.get(id)
        val dto = auditLogMapper.get(entity)
        return ResponseEntity.ok(dto)
    }

    override fun getCollection(
        user: UserEntity?,
        rsql: String?,
        pageNumber: Int?,
        pageSize: Int,
        sortProperty: String?,
        sortDirection: Direction
    ): ResponseEntity<Any> {
        TODO("Not yet implemented")
    }

}