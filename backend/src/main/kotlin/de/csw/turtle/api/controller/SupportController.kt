package de.csw.turtle.api.controller

import de.csw.turtle.api.dto.request.CreateSupportTicketRequest
import de.csw.turtle.api.dto.response.GetSupportTicketResponse
import de.csw.turtle.api.dto.response.GetUserResponse
import de.csw.turtle.api.exception.exceptions.support.TicketNotFoundException
import de.csw.turtle.api.repository.SupportTicketRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort.Direction
import org.springframework.http.ResponseEntity
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import kotlin.jvm.optionals.getOrNull

@RestController
@RequestMapping("/api/support")
class SupportController(
    private val supportTicketRepository: SupportTicketRepository
) {

    @PostMapping
    @Transactional
    fun create(
        @RequestBody createSupportTicketRequest: CreateSupportTicketRequest
    ): ResponseEntity<GetSupportTicketResponse> {
        val ticket = createSupportTicketRequest.create()
        supportTicketRepository.save(ticket)

        return ResponseEntity
            .created(URI.create("/api/support/${ticket.id}"))
            .body(GetSupportTicketResponse(ticket))
    }

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

    @GetMapping("/{id}")
    fun getById(
        @PathVariable id: Long
    ): ResponseEntity<GetSupportTicketResponse> {
        val ticket = supportTicketRepository.findById(id).getOrNull()
            ?: throw TicketNotFoundException(id)

        return ResponseEntity.ok(GetSupportTicketResponse(ticket))
    }

    @DeleteMapping("/{id}")
    @Transactional
    fun deleteById(
        @PathVariable id: Long
    ): ResponseEntity<Void> {
        supportTicketRepository.deleteById(id)
        return ResponseEntity.noContent().build()
    }

}