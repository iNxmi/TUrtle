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
import de.csw.turtle.api.mapper.ItemMapper
import de.csw.turtle.api.service.ItemService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/items")
class ItemController(
    private val itemService: ItemService,
    private val itemMapper: ItemMapper,
) : CreateController<ItemEntity, CreateItemRequest, GetItemResponse>,
    GetController<ItemEntity, Long, GetItemResponse>,
    PatchController<ItemEntity, PatchItemRequest, GetItemResponse>,
    DeleteController<ItemEntity> {

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

        val entity = itemService.create(request)
        val location = URI.create("/api/items/${entity.id}")
        val dto = itemMapper.get(entity)
        return ResponseEntity.created(location).body(dto)
    }

    override fun get(
        user: UserEntity?,

        variable: Long,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetItemResponse> {
        val entity = itemService.get(variable)
        val dto = itemMapper.get(entity)
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
            val dto = page.map { itemMapper.get(it) }
            return ResponseEntity.ok(dto)
        }

        val collection = itemService.getAll(rsql = rsql, sort = sort).toMutableSet()
        val dto = collection.map { itemMapper.get(it) }
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

        val entity = itemService.patch(id, request)
        val dto = itemMapper.get(entity)
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