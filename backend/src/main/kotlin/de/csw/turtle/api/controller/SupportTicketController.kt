package de.csw.turtle.api.controller

import de.csw.turtle.api.Permission
import de.csw.turtle.api.dto.create.CreateSupportTicketRequest
import de.csw.turtle.api.dto.get.GetSupportTicketResponse
import de.csw.turtle.api.dto.patch.PatchSupportTicketRequest
import de.csw.turtle.api.entity.SupportTicketEntity
import de.csw.turtle.api.entity.SupportTicketEntity.Status
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.service.AltchaService
import de.csw.turtle.api.service.NetworkService
import de.csw.turtle.api.service.SupportTicketService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.net.URI

private const val ENDPOINT = "/api/support-tickets"

@RestController
@RequestMapping(ENDPOINT)
class SupportTicketController(
    private val supportTicketService: SupportTicketService,
    private val altchaService: AltchaService,
    private val networkService: NetworkService
) : CreateController<SupportTicketEntity, CreateSupportTicketRequest, GetSupportTicketResponse>,
    GetController<SupportTicketEntity, Long, GetSupportTicketResponse>,
    PatchController<SupportTicketEntity, PatchSupportTicketRequest, GetSupportTicketResponse>,
    DeleteController<SupportTicketEntity> {

    @PostMapping
    override fun create(
        @AuthenticationPrincipal user: UserEntity?,

        @RequestBody request: CreateSupportTicketRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetSupportTicketResponse> {

        if (user == null) {
            val ipAddress = networkService.getClientIp(httpRequest)
            if (!altchaService.isTrusted(ipAddress))
                if (request.altchaToken == null || !altchaService.isValid(request.altchaToken))
                    throw HttpException.Forbidden("Invalid captcha token.")
        }

        val entity = supportTicketService.create(
            urgency = request.urgency,
            category = request.category,
            email = request.email,
            subject = request.subject,
            description = request.description,
            status = Status.OPEN
        )

        val location = URI.create("$ENDPOINT/${entity.id}")
        val dto = GetSupportTicketResponse(entity)
        return ResponseEntity.created(location).body(dto)
    }

    @GetMapping("/{variable}")
    override fun get(
        @AuthenticationPrincipal user: UserEntity?,

        @PathVariable variable: Long,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetSupportTicketResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        val entity = supportTicketService.getById(variable)
            ?: throw HttpException.NotFound()

        if (!user.hasPermission(Permission.MANAGE_SUPPORT_TICKETS))
            if (entity.email != user.email)
                throw HttpException.Forbidden()

        val dto = GetSupportTicketResponse(entity)
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
        if (user == null)
            throw HttpException.Unauthorized()

        val sort = sortProperty?.let {
            Sort.by(sortDirection, sortProperty)
        } ?: Sort.unsorted()

        val specification: Specification<SupportTicketEntity> =
            if (user.hasPermission(Permission.MANAGE_SUPPORT_TICKETS)) {
                Specification.unrestricted()
            } else Specification { root, _, builder ->
                builder.equal(root.get<String>("email"), user.email)
            }

        if (pageNumber != null) {
            val pageable = PageRequest.of(pageNumber, pageSize, sort)
            val page = supportTicketService.getPage(rsql = rsql, pageable = pageable, specification = specification)
            val dto = page.map { GetSupportTicketResponse(it) }
            return ResponseEntity.ok(dto)
        }

        val collection =
            supportTicketService.getAll(rsql = rsql, sort = sort, specification = specification).toMutableSet()
        val dto = collection.map { GetSupportTicketResponse(it) }
        return ResponseEntity.ok(dto)
    }

    @PatchMapping("/{id}")
    override fun patch(
        @AuthenticationPrincipal user: UserEntity?,

        @PathVariable id: Long,
        @RequestBody request: PatchSupportTicketRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetSupportTicketResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_SUPPORT_TICKETS))
            throw HttpException.Forbidden()

        val entity = supportTicketService.patch(
            id = id,
            urgency = request.urgency,
            category = request.category,
            email = request.email,
            subject = request.subject,
            description = request.description,
            status = request.status
        )

        val dto = GetSupportTicketResponse(entity)
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

        if (!user.hasPermission(Permission.MANAGE_SUPPORT_TICKETS))
            throw HttpException.Forbidden()

        supportTicketService.delete(id)
        return ResponseEntity.noContent().build()
    }

}