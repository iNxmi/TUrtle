package de.csw.turtle.api.controller.api

import de.csw.turtle.api.SimpleUserDetails
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.dto.get.GetAuditLogResponse
import de.csw.turtle.api.entity.AuditLogEntity
import org.springframework.data.domain.Sort.Direction
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/audit-logs")
class AuditLogController :
    GetController<AuditLogEntity, GetAuditLogResponse> {

    override fun get(
        userDetails: SimpleUserDetails?,
        id: Long
    ): ResponseEntity<GetAuditLogResponse> {
        TODO("Not yet implemented")
    }

    override fun getCollection(
        userDetails: SimpleUserDetails?,
        rsql: String?,
        pageNumber: Int?,
        pageSize: Int,
        sortProperty: String?,
        sortDirection: Direction
    ): ResponseEntity<Any> {
        TODO("Not yet implemented")
    }

}