package de.csw.turtle.api.controller

import de.csw.turtle.api.Permission
import de.csw.turtle.api.dto.create.CreateSupportTicketUrgencyRequest
import de.csw.turtle.api.dto.get.GetSupportTicketUrgencyResponse
import de.csw.turtle.api.dto.patch.PatchSupportTicketUrgencyRequest
import de.csw.turtle.api.entity.SupportTicketUrgencyEntity
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.service.SupportTicketUrgencyService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.net.URI

private const val ENDPOINT = "/api/support-ticket-urgencies"

@RestController
@RequestMapping(ENDPOINT)
class SupportTicketUrgencyController(
    private val supportTicketUrgencyService: SupportTicketUrgencyService
) : CreateController<SupportTicketUrgencyEntity, CreateSupportTicketUrgencyRequest, GetSupportTicketUrgencyResponse>,
    GetController<SupportTicketUrgencyEntity, Long, GetSupportTicketUrgencyResponse>,
    PatchController<SupportTicketUrgencyEntity, PatchSupportTicketUrgencyRequest, GetSupportTicketUrgencyResponse>,
    DeleteController<SupportTicketUrgencyEntity> {

    @PostMapping
    override fun create(
        @AuthenticationPrincipal user: UserEntity?,

        @RequestBody request: CreateSupportTicketUrgencyRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetSupportTicketUrgencyResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_SUPPORT_TICKET_CATEGORIES))
            throw HttpException.Forbidden()

        val entity = supportTicketUrgencyService.create(
            name = request.name
        )

        val location = URI.create("$ENDPOINT/${entity.id}")
        val dto = GetSupportTicketUrgencyResponse(entity)
        return ResponseEntity.created(location).body(dto)
    }

    @GetMapping("/{variable}")
    override fun get(
        @AuthenticationPrincipal user: UserEntity?,

        @PathVariable variable: Long,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetSupportTicketUrgencyResponse> {
        val entity = supportTicketUrgencyService.getById(variable)
            ?: throw HttpException.NotFound()

        val dto = GetSupportTicketUrgencyResponse(entity)
        return ResponseEntity.ok(dto)
    }

    @GetMapping
    override fun getCollection(
        @AuthenticationPrincipal user: UserEntity?,

        @RequestParam rsql: String?,
        @RequestParam pageNumber: Int?,
        @RequestParam pageSize: Int,
        @RequestParam sortProperty: String?,
        @RequestParam sortDirection: Sort.Direction,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<Any> {
        val sort = sortProperty?.let {
            Sort.by(sortDirection, sortProperty)
        } ?: Sort.unsorted()

        if (pageNumber != null) {
            val pageable = PageRequest.of(pageNumber, pageSize, sort)
            val page = supportTicketUrgencyService.getPage(rsql = rsql, pageable = pageable)
            val dto = page.map { GetSupportTicketUrgencyResponse(it) }
            return ResponseEntity.ok(dto)
        }

        val collection = supportTicketUrgencyService.getAll(rsql = rsql, sort = sort).toMutableSet()
        val dto = collection.map { GetSupportTicketUrgencyResponse(it) }
        return ResponseEntity.ok(dto)
    }

    @PatchMapping("/id")
    override fun patch(
        @AuthenticationPrincipal user: UserEntity?,

        @PathVariable id: Long,
        @RequestBody request: PatchSupportTicketUrgencyRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetSupportTicketUrgencyResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_SUPPORT_TICKET_CATEGORIES))
            throw HttpException.Forbidden()

        val entity = supportTicketUrgencyService.patch(
            id = id,
            name = request.name
        )

        val dto = GetSupportTicketUrgencyResponse(entity)
        return ResponseEntity.ok(dto)
    }

    @DeleteMapping("/{id}")
    override fun delete(
        @AuthenticationPrincipal user: UserEntity?,

        @PathVariable id: Long,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<Void> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_SUPPORT_TICKET_CATEGORIES))
            throw HttpException.Forbidden()

        supportTicketUrgencyService.delete(id)
        return ResponseEntity.noContent().build()
    }

}