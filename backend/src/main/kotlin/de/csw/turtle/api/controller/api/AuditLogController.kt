package de.csw.turtle.api.controller.api

import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.dto.get.GetAuditLogResponse
import de.csw.turtle.api.entity.AuditLogEntity
import de.csw.turtle.api.mapper.AuditLogMapper
import de.csw.turtle.api.service.AuditLogService
import org.springframework.data.domain.Sort.Direction
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/audit-logs")
class AuditLogController(
    override val service: AuditLogService,
    override val mapper: AuditLogMapper
) : GetController<AuditLogEntity, GetAuditLogResponse> {

    @PreAuthorize("hasAuthority('MANAGE_AUDIT_LOGS')")
    override fun get(id: Long) = super.get(id)

    @PreAuthorize("hasAuthority('MANAGE_AUDIT_LOGS')")
    override fun getCollection(
        rsql: String?,

        pageNumber: Int?,
        pageSize: Int,

        sortProperty: String?,
        sortDirection: Direction
    ) = super.getCollection(rsql, pageNumber, pageSize, sortProperty, sortDirection)

}