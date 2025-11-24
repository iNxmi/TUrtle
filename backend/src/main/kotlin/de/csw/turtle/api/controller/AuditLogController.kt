package de.csw.turtle.api.controller

import de.csw.turtle.api.dto.response.GetAuditLogResponse
import de.csw.turtle.api.exception.exceptions.exception.ExceptionNotFoundException
import de.csw.turtle.api.repository.AuditLogRepository
import de.csw.turtle.api.security.Permission
import de.csw.turtle.api.security.Permission.*
import de.csw.turtle.api.security.RequiresPermission
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort.Direction
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import kotlin.jvm.optionals.getOrNull

@RestController
@RequestMapping("/api/auditlogs")
class AuditLogController(
    private val auditLogRepository: AuditLogRepository
) {


    @RequiresPermission(API_AUDITLOGS_GET)
    @GetMapping
    fun getPaginated(
        @RequestParam(name = "page") pageNumber: Int = 0,
        @RequestParam(name = "size") pageSize: Int = 20,
        @RequestParam(name = "sort") sort: Array<String> = emptyArray(),
        @RequestParam(name = "direction") direction: Direction = Direction.DESC
    ): ResponseEntity<Page<GetAuditLogResponse>> {
        val pageRequest = if (sort.isEmpty()) {
            PageRequest.of(pageNumber, pageSize)
        } else {
            PageRequest.of(pageNumber, pageSize, direction, *sort)
        }
        val page = auditLogRepository.findAll(pageRequest).map { GetAuditLogResponse(it) }
        return ResponseEntity.ok(page)
    }

    @RequiresPermission(API_AUDITLOGS_GET)
    @GetMapping("/{id}")
    fun get(@PathVariable id: Long): ResponseEntity<GetAuditLogResponse> {
        val entity = auditLogRepository.findById(id).getOrNull()
            ?: throw ExceptionNotFoundException(id)

        return ResponseEntity.ok(GetAuditLogResponse(entity))
    }

}