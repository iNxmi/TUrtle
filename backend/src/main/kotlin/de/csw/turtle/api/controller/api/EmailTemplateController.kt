package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Permission
import de.csw.turtle.api.controller.CreateController
import de.csw.turtle.api.controller.DeleteController
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.controller.PatchController
import de.csw.turtle.api.dto.create.CreateEmailTemplateRequest
import de.csw.turtle.api.dto.get.GetEmailTemplateResponse
import de.csw.turtle.api.dto.patch.PatchEmailTemplateRequest
import de.csw.turtle.api.entity.EmailTemplateEntity
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.mapper.EmailTemplateMapper
import de.csw.turtle.api.service.EmailTemplateService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/email-templates")
class EmailTemplateController(
    private val emailTemplateService: EmailTemplateService,
    private val emailTemplateMapper: EmailTemplateMapper
) : CreateController<EmailTemplateEntity, CreateEmailTemplateRequest, GetEmailTemplateResponse>,
    GetController<EmailTemplateEntity, Long, GetEmailTemplateResponse>,
    PatchController<EmailTemplateEntity, PatchEmailTemplateRequest, GetEmailTemplateResponse>,
    DeleteController<EmailTemplateEntity> {

    override fun create(
        user: UserEntity?,

        request: CreateEmailTemplateRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetEmailTemplateResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_EMAIL_TEMPLATES))
            throw HttpException.Forbidden()

        val entity = emailTemplateService.create(request)
        val location = URI.create("/api/email-templates/${entity.id}")
        val dto = emailTemplateMapper.get(entity)
        return ResponseEntity.created(location).body(dto)
    }

    override fun get(
        user: UserEntity?,

        variable: Long,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetEmailTemplateResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_EMAIL_TEMPLATES))
            throw HttpException.Forbidden()

        val entity = emailTemplateService.get(variable)
        val dto = emailTemplateMapper.get(entity)
        return ResponseEntity.ok(dto)
    }

    override fun getCollection(
        user: UserEntity?,

        rsql: String?,
        pageNumber: Int?,
        pageSize: Int,
        sortProperty: String?,
        sortDirection: Sort.Direction,

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
            val dto = page.map { emailTemplateMapper.get(it) }
            return ResponseEntity.ok(dto)
        }

        val collection = emailTemplateService.getAll(rsql = rsql, sort = sort).toMutableSet()
        val dto = collection.map { emailTemplateMapper.get(it) }
        return ResponseEntity.ok(dto)
    }

    override fun patch(
        user: UserEntity?,

        id: Long,
        request: PatchEmailTemplateRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetEmailTemplateResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_EMAIL_TEMPLATES))
            throw HttpException.Forbidden()

        val entity = emailTemplateService.patch(id, request)
        val dto = emailTemplateMapper.get(entity)
        return ResponseEntity.ok(dto)
    }

    override fun delete(
        user: UserEntity?,

        id: Long,

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