package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Permission
import de.csw.turtle.api.controller.CreateController
import de.csw.turtle.api.controller.DeleteController
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.controller.PatchController
import de.csw.turtle.api.dto.create.CreateItemRequest
import de.csw.turtle.api.dto.get.GetItemResponse
import de.csw.turtle.api.dto.patch.PatchItemRequest
import de.csw.turtle.api.entity.ItemEntity
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.service.ItemService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

private const val ENDPOINT = "/api/items"

@RestController
@RequestMapping(ENDPOINT)
class ItemController(
    private val itemService: ItemService
) : CreateController<ItemEntity, CreateItemRequest, GetItemResponse>,
    GetController<ItemEntity, Long, GetItemResponse>,
    PatchController<ItemEntity, PatchItemRequest, GetItemResponse>,
    DeleteController<ItemEntity> {

    private val maxNameLength = 64
    private val maxDescriptionLength = 256

    override fun create(
        user: UserEntity?,

        request: CreateItemRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetItemResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_ITEMS))
            throw HttpException.Forbidden()

        if (request.name.isBlank() || request.name.length > maxNameLength)
            throw HttpException.BadRequest("Name cannot be blank and cannot exceed $maxNameLength characters.")

        if (itemService.getByNameOrNull(request.name) != null)
            throw HttpException.Conflict("Name '${request.name}' already exists.")

        if (request.description.length > maxDescriptionLength)
            throw HttpException.BadRequest("Description cannot exceed $maxDescriptionLength characters.")

        val entity = itemService.create(
            name = request.name,
            description = request.description,
            categoryId = request.categoryId,
            lockerId = request.lockerId,
            acquiredAt = request.acquiredAt
        )

        val location = URI.create("$ENDPOINT/${entity.id}")
        val dto = GetItemResponse(entity)
        return ResponseEntity.created(location).body(dto)
    }

    override fun get(
        user: UserEntity?,

        variable: Long,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetItemResponse> {
        val entity = itemService.getById(variable)
            ?: throw HttpException.NotFound()

        val dto = GetItemResponse(entity)
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
            val page = itemService.getPage(rsql = rsql, pageable = pageable)
            val dto = page.map { GetItemResponse(it) }
            return ResponseEntity.ok(dto)
        }

        val collection = itemService.getAll(rsql = rsql, sort = sort).toMutableSet()
        val dto = collection.map { GetItemResponse(it) }
        return ResponseEntity.ok(dto)
    }

    override fun patch(
        user: UserEntity?,

        id: Long,
        request: PatchItemRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetItemResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_ITEMS))
            throw HttpException.Forbidden()

        if (request.name != null)
            if (request.name.isBlank() || request.name.length > maxNameLength)
                throw HttpException.BadRequest("Name cannot be blank and cannot exceed $maxNameLength characters.")
            else if (itemService.getByNameOrNull(request.name) != null)
                throw HttpException.Conflict("Name '${request.name}' already exists.")

        if (request.description != null)
            if (request.description.length > maxDescriptionLength)
                throw HttpException.BadRequest("Description cannot exceed $maxDescriptionLength characters.")

        val entity = itemService.patch(
            id = id,
            name = request.name,
            description = request.description,
            categoryId = request.categoryId,
            lockerId = request.lockerId,
            acquiredAt = request.acquiredAt
        )

        val dto = GetItemResponse(entity)
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

        if (!user.hasPermission(Permission.MANAGE_ITEMS))
            throw HttpException.Forbidden()

        itemService.delete(id)
        return ResponseEntity.noContent().build()
    }

}