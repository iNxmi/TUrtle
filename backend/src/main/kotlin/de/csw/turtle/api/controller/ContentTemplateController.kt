package de.csw.turtle.api.controller

import de.csw.turtle.api.Permission
import de.csw.turtle.api.dto.create.CreateContentTemplateRequest
import de.csw.turtle.api.dto.get.GetContentTemplateResponse
import de.csw.turtle.api.dto.patch.PatchContentTemplateRequest
import de.csw.turtle.api.entity.ContentTemplateEntity
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.service.ContentTemplateService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.net.URI

private const val ENDPOINT = "/api/content-templates"

@RestController
@RequestMapping(ENDPOINT)
class ContentTemplateController(
    private val contentTemplateService: ContentTemplateService
) : CreateController<ContentTemplateEntity, CreateContentTemplateRequest, GetContentTemplateResponse>,
    GetController<ContentTemplateEntity, Long, GetContentTemplateResponse>,
    PatchController<ContentTemplateEntity, PatchContentTemplateRequest, GetContentTemplateResponse>,
    DeleteController<ContentTemplateEntity> {

    @PostMapping
    override fun create(
        @AuthenticationPrincipal user: UserEntity?,

        @RequestBody request: CreateContentTemplateRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetContentTemplateResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_GENERAL_TEMPLATES))
            throw HttpException.Forbidden()

        if (contentTemplateService.getByName(request.name) != null)
            throw HttpException.Conflict("General template with name '${request.name}' already exists.")

        val entity = contentTemplateService.create(
            name = request.name,
            description = request.description,
            content = request.content,
            type = request.type
        )

        val location = URI.create("$ENDPOINT/${entity.id}")
        val dto = GetContentTemplateResponse(entity)
        return ResponseEntity.created(location).body(dto)
    }

    @GetMapping("/{variable}")
    override fun get(
        @AuthenticationPrincipal user: UserEntity?,

        @PathVariable variable: Long,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetContentTemplateResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_GENERAL_TEMPLATES))
            throw HttpException.Forbidden()

        val entity = contentTemplateService.getById(variable)
            ?: throw HttpException.NotFound()

        val dto = GetContentTemplateResponse(entity)
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

        if (!user.hasPermission(Permission.MANAGE_GENERAL_TEMPLATES))
            throw HttpException.Forbidden()

        val sort = sortProperty?.let {
            Sort.by(sortDirection, sortProperty)
        } ?: Sort.unsorted()

        if (pageNumber != null) {
            val pageable = PageRequest.of(pageNumber, pageSize, sort)
            val page = contentTemplateService.getPage(rsql = rsql, pageable = pageable)
            val dto = page.map { GetContentTemplateResponse(it) }
            return ResponseEntity.ok(dto)
        }

        val collection = contentTemplateService.getAll(rsql = rsql, sort = sort).toMutableSet()
        val dto = collection.map { GetContentTemplateResponse(it) }
        return ResponseEntity.ok(dto)
    }

    @PatchMapping("/{id}")
    override fun patch(
        @AuthenticationPrincipal user: UserEntity?,

        @PathVariable id: Long,
        @RequestBody request: PatchContentTemplateRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetContentTemplateResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_GENERAL_TEMPLATES))
            throw HttpException.Forbidden()

        if (request.name != null)
            if (contentTemplateService.getByName(request.name) != null)
                throw HttpException.Conflict("General template with name '${request.name}' already exists.")

        val entity = contentTemplateService.patch(
            id = id,
            name = request.name,
            description = request.description,
            content = request.content,
            type = request.type
        )

        val dto = GetContentTemplateResponse(entity)
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

        if (!user.hasPermission(Permission.MANAGE_GENERAL_TEMPLATES))
            throw HttpException.Forbidden()

        contentTemplateService.delete(id)
        return ResponseEntity.noContent().build()
    }

}