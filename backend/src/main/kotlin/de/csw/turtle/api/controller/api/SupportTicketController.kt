package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Permission
import de.csw.turtle.api.controller.CreateController
import de.csw.turtle.api.controller.DeleteController
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.controller.PatchController
import de.csw.turtle.api.dto.create.CreateSupportTicketRequest
import de.csw.turtle.api.dto.get.GetSupportTicketResponse
import de.csw.turtle.api.dto.patch.PatchSupportTicketRequest
import de.csw.turtle.api.entity.SupportTicketEntity
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.mapper.SupportTicketMapper
import de.csw.turtle.api.service.AltchaService
import de.csw.turtle.api.service.SupportTicketService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/support-tickets")
class SupportTicketController(
    private val supportTicketService: SupportTicketService,
    private val supportTicketMapper: SupportTicketMapper,
    private val altchaService: AltchaService
) : CreateController<SupportTicketEntity, CreateSupportTicketRequest, GetSupportTicketResponse>,
    GetController<SupportTicketEntity, Long, GetSupportTicketResponse>,
    PatchController<SupportTicketEntity, PatchSupportTicketRequest, GetSupportTicketResponse>,
    DeleteController<SupportTicketEntity> {

    override fun create(
        user: UserEntity?,
        request: CreateSupportTicketRequest
    ): ResponseEntity<GetSupportTicketResponse> {

        if (user == null)
            if (request.altchaToken == null || altchaService.isValid(request.altchaToken))
                throw HttpException.Forbidden("Invalid captcha token.")

        val entity = supportTicketService.create(request)
        val location = URI.create("/api/support-tickets/${entity.id}")
        val dto = supportTicketMapper.get(entity)
        return ResponseEntity.created(location).body(dto)
    }

    override fun get(
        user: UserEntity?,
        variable: Long
    ): ResponseEntity<GetSupportTicketResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        val entity = supportTicketService.get(variable)

        if (!user.hasPermission(Permission.MANAGE_SUPPORT_TICKETS))
            if (entity.email != user.email)
                throw HttpException.Forbidden()

        val dto = supportTicketMapper.get(entity)
        return ResponseEntity.ok(dto)
    }

    override fun getCollection(
        user: UserEntity?,
        rsql: String?,
        pageNumber: Int?,
        pageSize: Int,
        sortProperty: String?,
        sortDirection: Sort.Direction
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
            val dto = page.map { supportTicketMapper.get(it) }
            return ResponseEntity.ok(dto)
        }

        val collection =
            supportTicketService.getAll(rsql = rsql, sort = sort, specification = specification).toMutableSet()
        val dto = collection.map { supportTicketMapper.get(it) }
        return ResponseEntity.ok(dto)
    }

    override fun patch(
        user: UserEntity?,
        id: Long,
        request: PatchSupportTicketRequest
    ): ResponseEntity<GetSupportTicketResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_SUPPORT_TICKETS))
            throw HttpException.Forbidden()

        val updated = supportTicketService.patch(id, request)
        val dto = supportTicketMapper.get(updated)
        return ResponseEntity.ok(dto)
    }

    override fun delete(
        user: UserEntity?,
        id: Long
    ): ResponseEntity<Void> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_SUPPORT_TICKETS))
            throw HttpException.Forbidden()

        supportTicketService.delete(id)
        return ResponseEntity.noContent().build()
    }

}