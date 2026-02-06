package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Permission
import de.csw.turtle.api.controller.CreateController
import de.csw.turtle.api.controller.DeleteController
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.controller.PatchController
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
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

private const val ENDPOINT ="/api/general-templates"

@RestController
@RequestMapping(ENDPOINT)
class GeneralTemplateController(
    private val generalTemplateService: GeneralTemplateService
) : CreateController<GeneralTemplateEntity, CreateGeneralTemplateRequest, GetGeneralTemplateResponse>,
    GetController<GeneralTemplateEntity, Long, GetGeneralTemplateResponse>,
    PatchController<GeneralTemplateEntity, PatchGeneralTemplateRequest, GetGeneralTemplateResponse>,
    DeleteController<GeneralTemplateEntity> {

    override fun create(
        user: UserEntity?,

        request: CreateGeneralTemplateRequest,

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

    override fun get(
        user: UserEntity?,

        variable: Long,

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

    override fun patch(
        user: UserEntity?,

        id: Long,
        request: PatchGeneralTemplateRequest,

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

    override fun delete(
        user: UserEntity?,

        id: Long,

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