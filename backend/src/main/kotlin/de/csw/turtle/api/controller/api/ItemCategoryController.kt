package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Permission
import de.csw.turtle.api.controller.CreateController
import de.csw.turtle.api.controller.DeleteController
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.controller.PatchController
import de.csw.turtle.api.dto.create.CreateItemCategoryRequest
import de.csw.turtle.api.dto.get.GetItemCategoryResponse
import de.csw.turtle.api.dto.patch.PatchItemCategoryRequest
import de.csw.turtle.api.entity.ItemCategoryEntity
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.mapper.ItemCategoryMapper
import de.csw.turtle.api.service.ItemCategoryService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/item-categories")
class ItemCategoryController(
    private val itemCategoryService: ItemCategoryService,
    private val itemCategoryMapper: ItemCategoryMapper
) :
    CreateController<ItemCategoryEntity, CreateItemCategoryRequest, GetItemCategoryResponse>,
    GetController<ItemCategoryEntity, Long, GetItemCategoryResponse>,
    PatchController<ItemCategoryEntity, PatchItemCategoryRequest, GetItemCategoryResponse>,
    DeleteController<ItemCategoryEntity> {

    override fun create(
        user: UserEntity?,

        request: CreateItemCategoryRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetItemCategoryResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_ITEM_CATEGORIES))
            throw HttpException.Forbidden()

        val entity = itemCategoryService.create(request)
        val location = URI.create("/api/item-categories/${entity.id}")
        val dto = itemCategoryMapper.get(entity)
        return ResponseEntity.created(location).body(dto)
    }

    override fun get(
        user: UserEntity?,

        variable: Long,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetItemCategoryResponse> {
        val entity = itemCategoryService.get(variable)
        val dto = itemCategoryMapper.get(entity)
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
            val page = itemCategoryService.getPage(rsql = rsql, pageable = pageable)
            val dto = page.map { itemCategoryMapper.get(it) }
            return ResponseEntity.ok(dto)
        }

        val collection = itemCategoryService.getAll(rsql = rsql, sort = sort).toMutableSet()
        val dto = collection.map { itemCategoryMapper.get(it) }
        return ResponseEntity.ok(dto)
    }

    override fun patch(
        user: UserEntity?,

        id: Long,
        request: PatchItemCategoryRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetItemCategoryResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_ITEM_CATEGORIES))
            throw HttpException.Forbidden()

        val entity = itemCategoryService.patch(id, request)
        val dto = itemCategoryMapper.get(entity)
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

        if (!user.hasPermission(Permission.MANAGE_ITEM_CATEGORIES))
            throw HttpException.Forbidden()

        itemCategoryService.delete(id)
        return ResponseEntity.noContent().build()
    }

}