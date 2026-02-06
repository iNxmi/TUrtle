package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Permission
import de.csw.turtle.api.controller.CreateController
import de.csw.turtle.api.controller.DeleteController
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.controller.PatchController
import de.csw.turtle.api.dto.create.CreateFAQRequest
import de.csw.turtle.api.dto.get.GetFAQResponse
import de.csw.turtle.api.dto.patch.PatchFAQRequest
import de.csw.turtle.api.entity.FAQEntity
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.service.FAQService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

private const val ENDPOINT = "/api/faq"

@RestController
@RequestMapping(ENDPOINT)
class FAQController(
    private val faqService: FAQService
) : CreateController<FAQEntity, CreateFAQRequest, GetFAQResponse>,
    GetController<FAQEntity, Long, GetFAQResponse>,
    PatchController<FAQEntity, PatchFAQRequest, GetFAQResponse>,
    DeleteController<FAQEntity> {

    //TODO replace by system setting
    private val maxNameLength = 64
    private val maxTitleLength = 64

    override fun create(
        user: UserEntity?,

        request: CreateFAQRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetFAQResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_FAQ))
            throw HttpException.Forbidden()

        if (request.name.isBlank() || request.name.length > maxNameLength)
            throw HttpException.BadRequest("Name is required and cannot exceed $maxNameLength characters.")

        if (faqService.getByName(request.name) != null)
            throw HttpException.Conflict("Name '${request.name}' already exists.")

        if (request.title.isBlank() || request.title.length > maxTitleLength)
            throw HttpException.BadRequest("Title is required and cannot exceed $maxTitleLength characters.")

        if (request.content.isBlank())
            throw HttpException.BadRequest("Content is required.")

        val entity = faqService.create(request.name, request.title, request.content)
        val location = URI.create("$ENDPOINT/${entity.id}")
        val dto = GetFAQResponse(entity)
        return ResponseEntity.created(location).body(dto)
    }

    override fun get(
        user: UserEntity?,

        variable: Long,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetFAQResponse> {
        val entity = faqService.getById(variable)
            ?: throw HttpException.NotFound()

        val dto = GetFAQResponse(entity)
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
        val sort = sortProperty?.let {
            Sort.by(sortDirection, sortProperty)
        } ?: Sort.unsorted()

        if (pageNumber != null) {
            val pageable = PageRequest.of(pageNumber, pageSize, sort)
            val page = faqService.getPage(rsql = rsql, pageable = pageable)
            val dto = page.map { GetFAQResponse(it) }
            return ResponseEntity.ok(dto)
        }

        val collection = faqService.getAll(rsql = rsql, sort = sort).toMutableSet()
        val dto = collection.map { GetFAQResponse(it) }
        return ResponseEntity.ok(dto)
    }

    override fun patch(
        user: UserEntity?,

        id: Long,
        request: PatchFAQRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetFAQResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_FAQ))
            throw HttpException.Forbidden()

        if (request.name != null) {
            if (request.name.isBlank() || request.name.length > maxNameLength)
                throw HttpException.BadRequest("Name is required and cannot exceed $maxNameLength characters.")

            if (faqService.getByName(request.name) != null)
                throw HttpException.Conflict("Name '${request.name}' already exists.")
        }

        if (request.title != null)
            if (request.title.isBlank() || request.title.length > maxTitleLength)
                throw HttpException.BadRequest("Title is required and cannot exceed $maxTitleLength characters.")

        if (request.content != null)
            if (request.content.isBlank())
                throw HttpException.BadRequest("Content is required.")

        val entity = faqService.patch(
            id = id,
            name = request.name,
            title = request.title,
            content = request.content
        )

        val dto = GetFAQResponse(entity)
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

        if (!user.hasPermission(Permission.MANAGE_FAQ))
            throw HttpException.Forbidden()

        faqService.delete(id)
        return ResponseEntity.noContent().build()
    }

}