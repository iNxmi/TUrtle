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
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.mapper.ItemBookingMapper
import de.csw.turtle.api.service.ItemBookingService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/item-bookings")
class ItemBookingController(
    private val itemBookingService: ItemBookingService,
    private val itemBookingMapper: ItemBookingMapper
) : CreateController<ItemBookingEntity, CreateItemBookingRequest, GetItemBookingResponse>,
    GetController<ItemBookingEntity, Long, GetItemBookingResponse>,
    PatchController<ItemBookingEntity, PatchItemBookingRequest, GetItemBookingResponse>,
    DeleteController<ItemBookingEntity> {

    override fun create(
        user: UserEntity?,
        request: CreateItemBookingRequest
    ): ResponseEntity<GetItemBookingResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        val sanitized = if (user.hasPermission(Permission.MANAGE_ITEM_BOOKINGS)) {
            request
        } else {
            CreateItemBookingRequest(
                start = request.start,
                end = request.end,
                itemId = request.itemId,
                userId = user.id,
                status = ItemBookingEntity.Status.RESERVED
            )
        }

        val entity = itemBookingService.create(sanitized)
        val location = URI.create("/api/item-bookings/${entity.id}")
        val dto = itemBookingMapper.get(entity)
        return ResponseEntity.created(location).body(dto)
    }

    override fun get(
        user: UserEntity?,
        variable: Long
    ): ResponseEntity<GetItemBookingResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        val entity = itemBookingService.get(variable)

        if (!user.hasPermission(Permission.MANAGE_ITEM_BOOKINGS))
            if (entity.user != user)
                throw HttpException.Forbidden()

        val dto = itemBookingMapper.get(entity)
        return ResponseEntity.ok(dto)
    }

    override fun getCollection(
        user: UserEntity?,
        rsql: String?,
        pageNumber: Int?,
        pageSize: Int,
        sortProperty: String?,
        sortDirection: Sort.Direction
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
            val dto = page.map { itemBookingMapper.get(it) }
            return ResponseEntity.ok(dto)
        }

        val collection =
            itemBookingService.getAll(rsql = rsql, sort = sort, specification = specification).toMutableSet()
        val dto = collection.map { itemBookingMapper.get(it) }
        return ResponseEntity.ok(dto)
    }

    override fun patch(
        user: UserEntity?,
        id: Long,
        request: PatchItemBookingRequest
    ): ResponseEntity<GetItemBookingResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        val entity = itemBookingService.get(id)

        val sanitized = if (!user.hasPermission(Permission.MANAGE_ITEM_BOOKINGS)) {
            if (entity.user != user)
                throw HttpException.Forbidden()

            request.copy(userId = null, status = null)
        } else request

        val updated = itemBookingService.patch(id, sanitized)
        val dto = itemBookingMapper.get(updated)
        return ResponseEntity.ok(dto)
    }

    override fun delete(
        user: UserEntity?,
        id: Long
    ): ResponseEntity<Void> {
        if (user == null)
            throw HttpException.Unauthorized()

        val entity = itemBookingService.get(id)

        if (!user.hasPermission(Permission.MANAGE_ITEM_BOOKINGS))
            if (entity.user != user)
                throw HttpException.Forbidden()

        itemBookingService.delete(id)
        return ResponseEntity.noContent().build()
    }

}