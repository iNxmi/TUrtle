package de.csw.turtle.api.controller

import de.csw.turtle.api.Permission
import de.csw.turtle.api.dto.create.CreateGeneralTemplateRequest
import de.csw.turtle.api.dto.get.GetGeneralTemplateResponse
import de.csw.turtle.api.dto.patch.PatchGeneralTemplateRequest
import de.csw.turtle.api.entity.GeneralTemplateEntity
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.service.GeneralTemplateService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.net.URI

private const val ENDPOINT = "/api/general-templates"

@RestController
@RequestMapping(ENDPOINT)
class GeneralTemplateController(
    private val generalTemplateService: GeneralTemplateService
) : CreateController<GeneralTemplateEntity, CreateGeneralTemplateRequest, GetGeneralTemplateResponse>,
    GetController<GeneralTemplateEntity, Long, GetGeneralTemplateResponse>,
    PatchController<GeneralTemplateEntity, PatchGeneralTemplateRequest, GetGeneralTemplateResponse>,
    DeleteController<GeneralTemplateEntity> {

    @PostMapping
    override fun create(
        @AuthenticationPrincipal user: UserEntity?,

        @RequestBody request: CreateGeneralTemplateRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetGeneralTemplateResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_GENERAL_TEMPLATES))
            throw HttpException.Forbidden()

        if (generalTemplateService.getByNameOrNull(request.name) != null)
            throw HttpException.Conflict("General template with name '${request.name}' already exists.")

        val entity = generalTemplateService.create(
            name = request.name,
            description = request.description,
            content = request.content
        )

        val location = URI.create("$ENDPOINT/${entity.id}")
        val dto = GetGeneralTemplateResponse(entity)
        return ResponseEntity.created(location).body(dto)
    }

    @GetMapping("/{variable}")
    override fun get(
        @AuthenticationPrincipal user: UserEntity?,

        @PathVariable variable: Long,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetGeneralTemplateResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_GENERAL_TEMPLATES))
            throw HttpException.Forbidden()

        val entity = generalTemplateService.getById(variable)
            ?: throw HttpException.NotFound()

        val dto = GetGeneralTemplateResponse(entity)
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
            val page = generalTemplateService.getPage(rsql = rsql, pageable = pageable)
            val dto = page.map { GetGeneralTemplateResponse(it) }
            return ResponseEntity.ok(dto)
        }

        val collection = generalTemplateService.getAll(rsql = rsql, sort = sort).toMutableSet()
        val dto = collection.map { GetGeneralTemplateResponse(it) }
        return ResponseEntity.ok(dto)
    }

    @PatchMapping("/{id}")
    override fun patch(
        @AuthenticationPrincipal user: UserEntity?,

        @PathVariable id: Long,
        @RequestBody request: PatchGeneralTemplateRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetGeneralTemplateResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_GENERAL_TEMPLATES))
            throw HttpException.Forbidden()

        if (request.name != null)
            if (generalTemplateService.getByNameOrNull(request.name) != null)
                throw HttpException.Conflict("General template with name '${request.name}' already exists.")

        val entity = generalTemplateService.patch(
            id = id,
            name = request.name,
            description = request.description,
            content = request.content
        )

        val dto = GetGeneralTemplateResponse(entity)
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

        generalTemplateService.delete(id)
        return ResponseEntity.noContent().build()
    }

}