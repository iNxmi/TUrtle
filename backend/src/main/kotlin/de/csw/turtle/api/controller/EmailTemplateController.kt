package de.csw.turtle.api.controller

import de.csw.turtle.api.Permission
import de.csw.turtle.api.dto.create.CreateEmailTemplateRequest
import de.csw.turtle.api.dto.get.GetEmailTemplateResponse
import de.csw.turtle.api.dto.patch.PatchEmailTemplateRequest
import de.csw.turtle.api.entity.EmailTemplateEntity
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.service.EmailTemplateService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.net.URI

private const val ENDPOINT = "/api/email-templates"

@RestController
@RequestMapping(ENDPOINT)
class EmailTemplateController(
    private val emailTemplateService: EmailTemplateService
) : CreateController<EmailTemplateEntity, CreateEmailTemplateRequest, GetEmailTemplateResponse>,
    GetController<EmailTemplateEntity, Long, GetEmailTemplateResponse>,
    PatchController<EmailTemplateEntity, PatchEmailTemplateRequest, GetEmailTemplateResponse>,
    DeleteController<EmailTemplateEntity> {

    @PostMapping
    override fun create(
        @AuthenticationPrincipal user: UserEntity?,

        @RequestBody request: CreateEmailTemplateRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetEmailTemplateResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_EMAIL_TEMPLATES))
            throw HttpException.Forbidden()

        val entity = emailTemplateService.create(
            name = request.name,
            description = request.description,
            subject = request.subject,
            content = request.content,
            type = request.type
        )

        val location = URI.create("$ENDPOINT/${entity.id}")
        val dto = GetEmailTemplateResponse(entity)
        return ResponseEntity.created(location).body(dto)
    }

    @GetMapping("/{variable}")
    override fun get(
        @AuthenticationPrincipal user: UserEntity?,

        @PathVariable variable: Long,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetEmailTemplateResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_EMAIL_TEMPLATES))
            throw HttpException.Forbidden()

        val entity = emailTemplateService.getById(variable)
            ?: throw HttpException.NotFound()

        val dto = GetEmailTemplateResponse(entity)
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

        if (!user.hasPermission(Permission.MANAGE_EMAIL_TEMPLATES))
            throw HttpException.Forbidden()

        val sort = sortProperty?.let {
            Sort.by(sortDirection, sortProperty)
        } ?: Sort.unsorted()

        if (pageNumber != null) {
            val pageable = PageRequest.of(pageNumber, pageSize, sort)
            val page = emailTemplateService.getPage(rsql = rsql, pageable = pageable)
            val dto = page.map { GetEmailTemplateResponse(it) }
            return ResponseEntity.ok(dto)
        }

        val collection = emailTemplateService.getAll(rsql = rsql, sort = sort).toMutableSet()
        val dto = collection.map { GetEmailTemplateResponse(it) }
        return ResponseEntity.ok(dto)
    }

    @PatchMapping("/{id}")
    override fun patch(
        @AuthenticationPrincipal user: UserEntity?,

        @PathVariable id: Long,
        @RequestBody request: PatchEmailTemplateRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetEmailTemplateResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_EMAIL_TEMPLATES))
            throw HttpException.Forbidden()

        val entity = emailTemplateService.patch(
            id = id,
            name = request.name,
            description = request.description,
            subject = request.subject,
            content = request.content,
            type = request.type
        )

        val dto = GetEmailTemplateResponse(entity)
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

        if (!user.hasPermission(Permission.MANAGE_EMAIL_TEMPLATES))
            throw HttpException.Forbidden()

        emailTemplateService.delete(id)
        return ResponseEntity.noContent().build()
    }

}