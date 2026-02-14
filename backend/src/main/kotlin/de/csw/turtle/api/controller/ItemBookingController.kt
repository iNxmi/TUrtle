package de.csw.turtle.api.controller

import de.csw.turtle.api.Permission
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
import java.time.Instant

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

        var status = if (item.needsConfirmation) Status.REQUESTED else Status.APPROVED
        var userId = user.id
        var collectedAt: Instant? = null
        var returnedAt: Instant? = null
        if (user.hasPermission(Permission.MANAGE_ITEM_BOOKINGS)) {
            request.userId?.let { userId = it }
            request.status?.let { status = it }
            request.collectedAt?.let { collectedAt = it }
            request.returnedAt?.let { returnedAt = it }
        } else {
            //TODO start >= now
            //TODO start < end
        }

        val entity = itemBookingService.create(
            userId = userId,
            itemId = request.itemId,
            start = request.start,
            end = request.end,
            collectedAt = collectedAt,
            returnedAt = returnedAt,
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
        var itemId: Long? = null
        var status: Status? = null
        var start: Instant? = null
        var end: Instant? = null
        var collectedAt: Instant? = null
        var returnedAt: Instant? = null
        if (user.hasPermission(Permission.MANAGE_ITEM_BOOKINGS)) {
            request.userId?.let { userId = it }
            request.itemId?.let { itemId = it }
            request.status?.let { status = it }
            request.start?.let { start = it }
            request.end?.let { end = it }
            request.collectedAt?.let { collectedAt = it }
            request.returnedAt?.let { returnedAt = it }
        } else {
            if (entity.user != user)
                throw HttpException.Forbidden()

            //TODO if(now > start) {
            //  request.start?.let { start = it }
            //  request.end?.let { end = it }
            //}
        }

        val updated = itemBookingService.patch(
            id = id,
            userId = userId,
            itemId = itemId,
            start = start,
            end = end,
            collectedAt = collectedAt,
            returnedAt = returnedAt,
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