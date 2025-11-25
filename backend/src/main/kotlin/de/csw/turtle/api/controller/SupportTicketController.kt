package de.csw.turtle.api.controller

import de.csw.turtle.api.dto.create.CreateSupportTicketRequest
import de.csw.turtle.api.dto.patch.PatchSupportTicketRequest
import de.csw.turtle.api.dto.get.GetSupportTicketResponse
import de.csw.turtle.api.entity.SupportTicketEntity
import de.csw.turtle.api.exception.exceptions.support.TicketNotFoundException
import de.csw.turtle.api.repository.SupportTicketRepository
import de.csw.turtle.api.security.Permission.*
import de.csw.turtle.api.security.RequiresPermission
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort.Direction
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.*
import java.net.URI
import kotlin.jvm.optionals.getOrNull

@RestController
@RequestMapping("/api/support")
class SupportTicketController(
    private val supportTicketRepository: SupportTicketRepository
) {

    @RequiresPermission(API_SUPPORT_CREATE)
    @PostMapping
    @Transactional
    fun create(
        @RequestBody request: CreateSupportTicketRequest
    ): ResponseEntity<GetSupportTicketResponse> {
        val entity = SupportTicketEntity(
            urgency = request.urgency,
            category = request.category,
            email = request.email,
            subject = request.subject,
            description = request.description
        )
        supportTicketRepository.save(entity)

        return ResponseEntity
            .created(URI.create("/api/support/${entity.id}"))
            .body(GetSupportTicketResponse(entity))
    }

    @RequiresPermission(API_SUPPORT_GET)
    @GetMapping
    fun getPaginated(
        @RequestParam(name = "page", required = false) pageNumber: Int = 0,
        @RequestParam(name = "size", required = false) pageSize: Int = 20,
        @RequestParam(name = "sort", required = false) sort: Array<String> = emptyArray(),
        @RequestParam(name = "direction", required = false) direction: Direction = Direction.DESC
    ): ResponseEntity<Page<GetSupportTicketResponse>> {
        val pageRequest = if (sort.isEmpty()) {
            PageRequest.of(pageNumber, pageSize)
        } else {
            PageRequest.of(pageNumber, pageSize, direction, *sort)
        }

        val page = supportTicketRepository.findAll(pageRequest).map { GetSupportTicketResponse(it) }
        return ResponseEntity.ok(page)
    }

    @RequiresPermission(API_SUPPORT_GET)
    @GetMapping("/{id}")
    fun getById(
        @PathVariable id: Long
    ): ResponseEntity<GetSupportTicketResponse> {
        val ticket = supportTicketRepository.findById(id).getOrNull()
            ?: throw TicketNotFoundException(id)

        return ResponseEntity.ok(GetSupportTicketResponse(ticket))
    }

    @RequiresPermission(API_SUPPORT_DELETE)
    @PatchMapping("/{id}")
    @Transactional
    fun deleteById(
        @PathVariable id: Long,
        @RequestBody request: PatchSupportTicketRequest
    ): ResponseEntity<GetSupportTicketResponse> {
        val ticket = supportTicketRepository.findById(id).getOrNull()
            ?: throw TicketNotFoundException(id)

        request.status?.let { ticket.status = it }

        supportTicketRepository.save(ticket)

        return ResponseEntity.ok(GetSupportTicketResponse(ticket))
    }

    @RequiresPermission(API_SUPPORT_DELETE)
    @DeleteMapping("/{id}")
    @Transactional
    fun deleteById(
        @PathVariable id: Long
    ): ResponseEntity<Void> {
        val ticket = supportTicketRepository.findById(id).getOrNull()
            ?: throw TicketNotFoundException(id)

        supportTicketRepository.delete(ticket)

        return ResponseEntity.noContent().build()
    }

}