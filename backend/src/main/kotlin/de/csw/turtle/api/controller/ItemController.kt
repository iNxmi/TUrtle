package de.csw.turtle.api.controller

import de.csw.turtle.api.Permission
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
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
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

    @PostMapping
    override fun create(
        @AuthenticationPrincipal user: UserEntity?,

        @RequestBody request: CreateItemRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetItemResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_ITEMS))
            throw HttpException.Forbidden()

        val entity = itemService.create(
            name = request.name,
            description = request.description,
            categoryId = request.categoryId,
            lockerId = request.lockerId,
            needsConfirmation = request.needsConfirmation,
            acquiredAt = request.acquiredAt
        )

        val location = URI.create("$ENDPOINT/${entity.id}")
        val dto = GetItemResponse(entity)
        return ResponseEntity.created(location).body(dto)
    }

    @GetMapping("/{variable}")
    override fun get(
        @AuthenticationPrincipal user: UserEntity?,

        @PathVariable variable: Long,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetItemResponse> {
        val entity = itemService.getById(variable)
            ?: throw HttpException.NotFound()

        val dto = GetItemResponse(entity)
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

    @PatchMapping("/{id}")
    override fun patch(
        @AuthenticationPrincipal user: UserEntity?,

        @PathVariable id: Long,
        @RequestBody request: PatchItemRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetItemResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_ITEMS))
            throw HttpException.Forbidden()

        val entity = itemService.patch(
            id = id,
            name = request.name,
            description = request.description,
            categoryId = request.categoryId,
            lockerId = request.lockerId,
            needsConfirmation = request.needsConfirmation,
            acquiredAt = request.acquiredAt
        )

        val dto = GetItemResponse(entity)
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

        if (!user.hasPermission(Permission.MANAGE_ITEMS))
            throw HttpException.Forbidden()

        itemService.delete(id)
        return ResponseEntity.noContent().build()
    }

}