package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Permission
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.dto.get.GetAuditLogResponse
import de.csw.turtle.api.entity.AuditLogEntity
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.service.AuditLogService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.domain.Sort.Direction
import org.springframework.data.jpa.domain.Specification
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/audit-logs")
class AuditLogController(
    private val auditLogService: AuditLogService
) : GetController<AuditLogEntity, Long, GetAuditLogResponse> {

    @GetMapping("/{variable}")
    override fun get(
        @AuthenticationPrincipal user: UserEntity?,

        @PathVariable variable: Long,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetAuditLogResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        val entity = auditLogService.getById(variable)
            ?: throw HttpException.NotFound() //TODO

        if (!user.hasPermission(Permission.MANAGE_AUDIT_LOGS))
            if (entity.user != user)
                throw HttpException.Forbidden() //TODO

        val dto = GetAuditLogResponse(entity)
        return ResponseEntity.ok(dto)
    }

    @GetMapping
    override fun getCollection(
        @AuthenticationPrincipal user: UserEntity?,

        @RequestParam rsql: String?,
        @RequestParam pageNumber: Int?,
        @RequestParam pageSize: Int,
        @RequestParam sortProperty: String?,
        @RequestParam sortDirection: Direction,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<Any> {
        if (user == null)
            throw HttpException.Unauthorized()

        val sort = sortProperty?.let {
            Sort.by(sortDirection, sortProperty)
        } ?: Sort.unsorted()

        val specification: Specification<AuditLogEntity> = if (user.hasPermission(Permission.MANAGE_AUDIT_LOGS)) {
            Specification.unrestricted()
        } else Specification { root, _, builder ->
            builder.equal(root.get<UserEntity>("user"), user)
        }

        if (pageNumber != null) {
            val pageable = PageRequest.of(pageNumber, pageSize, sort)
            val page = auditLogService.getPage(rsql = rsql, pageable = pageable, specification = specification)
            val dto = page.map { GetAuditLogResponse(it) }
            return ResponseEntity.ok(dto)
        }

        val collection = auditLogService.getAll(rsql = rsql, sort = sort, specification = specification).toMutableSet()
        val dto = collection.map { GetAuditLogResponse(it) }
        return ResponseEntity.ok(dto)
    }

}