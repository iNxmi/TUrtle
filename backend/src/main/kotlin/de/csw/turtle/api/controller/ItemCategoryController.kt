package de.csw.turtle.api.controller

import de.csw.turtle.api.Permission
import de.csw.turtle.api.dto.create.CreateItemCategoryRequest
import de.csw.turtle.api.dto.get.GetItemCategoryResponse
import de.csw.turtle.api.dto.patch.PatchItemCategoryRequest
import de.csw.turtle.api.entity.ItemCategoryEntity
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.service.ItemCategoryService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

private const val ENDPOINT = "/api/item-categories"

@RestController
@RequestMapping(ENDPOINT)
class ItemCategoryController(
    private val itemCategoryService: ItemCategoryService
) : CreateController<ItemCategoryEntity, CreateItemCategoryRequest, GetItemCategoryResponse>,
    GetController<ItemCategoryEntity, Long, GetItemCategoryResponse>,
    PatchController<ItemCategoryEntity, PatchItemCategoryRequest, GetItemCategoryResponse>,
    DeleteController<ItemCategoryEntity> {

    override fun create(
        @AuthenticationPrincipal user: UserEntity?,

        request: CreateItemCategoryRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetItemCategoryResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_ITEM_CATEGORIES))
            throw HttpException.Forbidden()

        if (itemCategoryService.getByNameOrNull(request.name) != null)
            throw HttpException.Conflict("Item category with name '${request.name}' already exists.")

        val entity = itemCategoryService.create(
            name = request.name
        )

        val location = URI.create("$ENDPOINT/${entity.id}")
        val dto = GetItemCategoryResponse(entity)
        return ResponseEntity.created(location).body(dto)
    }

    override fun get(
        @AuthenticationPrincipal user: UserEntity?,

        variable: Long,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetItemCategoryResponse> {
        val entity = itemCategoryService.getById(variable)
            ?: throw HttpException.NotFound()

        val dto = GetItemCategoryResponse(entity)
        return ResponseEntity.ok(dto)
    }

    override fun getCollection(
        @AuthenticationPrincipal user: UserEntity?,

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
            val page = itemCategoryService.getPage(rsql = rsql, pageable = pageable)
            val dto = page.map { GetItemCategoryResponse(it) }
            return ResponseEntity.ok(dto)
        }

        val collection = itemCategoryService.getAll(rsql = rsql, sort = sort).toMutableSet()
        val dto = collection.map { GetItemCategoryResponse(it) }
        return ResponseEntity.ok(dto)
    }

    override fun patch(
        @AuthenticationPrincipal user: UserEntity?,

        id: Long,
        request: PatchItemCategoryRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetItemCategoryResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_ITEM_CATEGORIES))
            throw HttpException.Forbidden()

        if (request.name != null)
            if (itemCategoryService.getByNameOrNull(request.name) != null)
                throw HttpException.Conflict("Item category with name '${request.name}' already exists.")

        val entity = itemCategoryService.patch(
            id = id,
            name = request.name
        )

        val dto = GetItemCategoryResponse(entity)
        return ResponseEntity.ok(dto)
    }

    override fun delete(
        @AuthenticationPrincipal user: UserEntity?,

        id: Long,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<Void> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_ITEM_CATEGORIES))
            throw HttpException.Forbidden()

        itemCategoryService.delete(id)
        return ResponseEntity.noContent().build()
    }

}