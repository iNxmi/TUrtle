package de.csw.turtle.api.controller

import de.csw.turtle.api.Permission
import de.csw.turtle.api.dto.create.CreateSupportTicketCategoryRequest
import de.csw.turtle.api.dto.get.GetSupportTicketCategoryResponse
import de.csw.turtle.api.dto.patch.PatchSupportTicketCategoryRequest
import de.csw.turtle.api.entity.SupportTicketCategoryEntity
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.service.SupportTicketCategoryService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.net.URI

private const val ENDPOINT = "/api/support-ticket-categories"

@RestController
@RequestMapping(ENDPOINT)
class SupportTicketCategoryController(
    private val supportTicketCategoryService: SupportTicketCategoryService
) : CreateController<SupportTicketCategoryEntity, CreateSupportTicketCategoryRequest, GetSupportTicketCategoryResponse>,
    GetController<SupportTicketCategoryEntity, Long, GetSupportTicketCategoryResponse>,
    PatchController<SupportTicketCategoryEntity, PatchSupportTicketCategoryRequest, GetSupportTicketCategoryResponse>,
    DeleteController<SupportTicketCategoryEntity> {

    @PostMapping
    override fun create(
        @AuthenticationPrincipal user: UserEntity?,

        @RequestBody request: CreateSupportTicketCategoryRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetSupportTicketCategoryResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_SUPPORT_TICKET_CATEGORIES))
            throw HttpException.Forbidden()

        val entity = supportTicketCategoryService.create(
            name = request.name
        )

        val location = URI.create("$ENDPOINT/${entity.id}")
        val dto = GetSupportTicketCategoryResponse(entity)
        return ResponseEntity.created(location).body(dto)
    }

    @GetMapping("/{variable}")
    override fun get(
        @AuthenticationPrincipal user: UserEntity?,

        @PathVariable variable: Long,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetSupportTicketCategoryResponse> {
        val entity = supportTicketCategoryService.getById(variable)
            ?: throw HttpException.NotFound()

        val dto = GetSupportTicketCategoryResponse(entity)
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
            val page = supportTicketCategoryService.getPage(rsql = rsql, pageable = pageable)
            val dto = page.map { GetSupportTicketCategoryResponse(it) }
            return ResponseEntity.ok(dto)
        }

        val collection = supportTicketCategoryService.getAll(rsql = rsql, sort = sort).toMutableSet()
        val dto = collection.map { GetSupportTicketCategoryResponse(it) }
        return ResponseEntity.ok(dto)
    }

    @PatchMapping("/id")
    override fun patch(
        @AuthenticationPrincipal user: UserEntity?,

        @PathVariable id: Long,
        @RequestBody request: PatchSupportTicketCategoryRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetSupportTicketCategoryResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_SUPPORT_TICKET_CATEGORIES))
            throw HttpException.Forbidden()

        val entity = supportTicketCategoryService.patch(
            id = id,
            name = request.name
        )

        val dto = GetSupportTicketCategoryResponse(entity)
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

        supportTicketCategoryService.delete(id)
        return ResponseEntity.noContent().build()
    }

}