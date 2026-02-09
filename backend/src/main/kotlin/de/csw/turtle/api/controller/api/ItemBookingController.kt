package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Permission
import de.csw.turtle.api.controller.CreateController
import de.csw.turtle.api.controller.DeleteController
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.controller.PatchController
import de.csw.turtle.api.dto.create.CreateItemBookingRequest
import de.csw.turtle.api.dto.get.GetItemBookingResponse
import de.csw.turtle.api.dto.patch.PatchItemBookingRequest
import de.csw.turtle.api.entity.ItemBookingEntity
import de.csw.turtle.api.entity.ItemBookingEntity.Status
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.service.ItemBookingService
import de.csw.turtle.api.service.ItemService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.net.URI

private const val ENDPOINT = "/api/item-bookings"

@RestController
@RequestMapping(ENDPOINT)
class ItemBookingController(
    private val itemBookingService: ItemBookingService,
    private val itemService: ItemService
) : CreateController<ItemBookingEntity, CreateItemBookingRequest, GetItemBookingResponse>,
    GetController<ItemBookingEntity, Long, GetItemBookingResponse>,
    PatchController<ItemBookingEntity, PatchItemBookingRequest, GetItemBookingResponse>,
    DeleteController<ItemBookingEntity> {

    @PostMapping
    override fun create(
        @AuthenticationPrincipal user: UserEntity?,

        @RequestBody request: CreateItemBookingRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetItemBookingResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        val item = itemService.getById(request.itemId)
            ?: throw HttpException.NotFound()

        var status = if (item.needsConfirmation) Status.REQUESTED else Status.RESERVED
        var userId = user.id
        if (user.hasPermission(Permission.MANAGE_ITEM_BOOKINGS)) {
            userId = request.userId
            status = request.status
        }

        if (request.start == request.end)
            throw HttpException.BadRequest("Start '${request.start}' cannot be the same as end '${request.end}'.")

        if (request.start.isAfter(request.end))
            throw HttpException.BadRequest("Start '${request.start}' cannot be after end '${request.end}'.")

        if (itemBookingService.getAllOverlapping(request.start, request.end, request.itemId, -1).isNotEmpty())
            throw HttpException.Conflict("Item with ID '${request.itemId}' is already booked between '${request.start}' and '${request.end}'")

        val entity = itemBookingService.create(
            userId = userId,
            itemId = request.itemId,
            start = request.start,
            end = request.end,
            status = status
        )

        val location = URI.create("$ENDPOINT/${entity.id}")
        val dto = GetItemBookingResponse(entity)
        return ResponseEntity.created(location).body(dto)
    }

    @GetMapping("/{variable}")
    override fun get(
        @AuthenticationPrincipal user: UserEntity?,

        @PathVariable variable: Long,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetItemBookingResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        val entity = itemBookingService.getById(variable)
            ?: throw HttpException.NotFound()

        if (!user.hasPermission(Permission.MANAGE_ITEM_BOOKINGS))
            if (entity.user != user)
                throw HttpException.Forbidden()

        val dto = GetItemBookingResponse(entity)
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

        val sort = sortProperty?.let {
            Sort.by(sortDirection, sortProperty)
        } ?: Sort.unsorted()

        val specification: Specification<ItemBookingEntity> =
            if (user.hasPermission(Permission.MANAGE_ITEM_BOOKINGS)) {
                Specification.unrestricted()
            } else Specification { root, _, builder ->
                builder.equal(root.get<UserEntity>("user"), user)
            }

        if (pageNumber != null) {
            val pageable = PageRequest.of(pageNumber, pageSize, sort)
            val page = itemBookingService.getPage(rsql = rsql, pageable = pageable, specification = specification)
            val dto = page.map { GetItemBookingResponse(it) }
            return ResponseEntity.ok(dto)
        }

        val collection =
            itemBookingService.getAll(rsql = rsql, sort = sort, specification = specification).toMutableSet()
        val dto = collection.map { GetItemBookingResponse(it) }
        return ResponseEntity.ok(dto)
    }

    @PatchMapping("/{id}")
    override fun patch(
        @AuthenticationPrincipal user: UserEntity?,

        @PathVariable id: Long,
        @RequestBody request: PatchItemBookingRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetItemBookingResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        val entity = itemBookingService.getById(id)
            ?: throw HttpException.NotFound()

        var userId: Long? = null
        var status: Status? = null
        if (!user.hasPermission(Permission.MANAGE_ITEM_BOOKINGS)) {
            if (entity.user != user)
                throw HttpException.Forbidden()

            request.userId?.let { userId = it }
            request.status?.let { status = it }
        }

        if (request.start != null && request.end != null) {
            if (request.start.isAfter(request.end))
                throw HttpException.BadRequest("Start '${request.start}' cannot be after end '${request.end}'.")

            if (request.start == request.end)
                throw HttpException.BadRequest("Start '${request.start}' cannot be the same as end '${request.end}'.")

            if (request.itemId != null)
                if (itemBookingService.getAllOverlapping(request.start, request.end, request.itemId, id).isNotEmpty())
                    throw HttpException.Conflict("Item with ID '${request.itemId}' is already booked between '${request.start}' and '${request.end}'")

            if (itemBookingService.getAllOverlapping(request.start, request.end, entity.item.id, id).isNotEmpty())
                throw HttpException.Conflict("Item with ID '${entity.item.id}' is already booked between '${request.start}' and '${request.end}'")
        }

        if (request.start != null && request.end == null && request.start.isAfter(entity.end))
            throw HttpException.BadRequest("Start '${request.start}' cannot be after end '${entity.end}'.")

        if (request.start == null && request.end != null && request.end.isBefore(entity.start))
            throw HttpException.BadRequest("End '${request.end}' cannot be before '${entity.start}'.")

        val updated = itemBookingService.patch(
            id = id,
            userId = userId,
            itemId = request.itemId,
            start = request.start,
            end = request.end,
            status = status
        )

        val dto = GetItemBookingResponse(updated)
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

        val entity = itemBookingService.getById(id)
            ?: throw HttpException.NotFound()

        if (!user.hasPermission(Permission.MANAGE_ITEM_BOOKINGS))
            if (entity.user != user)
                throw HttpException.Forbidden()

        itemBookingService.delete(id)
        return ResponseEntity.noContent().build()
    }

}