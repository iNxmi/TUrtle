package de.csw.turtle.api.mapper

import de.csw.turtle.api.dto.create.CreateItemBookingRequest
import de.csw.turtle.api.dto.get.GetItemBookingResponse
import de.csw.turtle.api.dto.patch.PatchItemBookingRequest
import de.csw.turtle.api.entity.ItemBookingEntity
import de.csw.turtle.api.service.ItemService
import de.csw.turtle.api.service.UserService
import org.mapstruct.Mapper
import org.springframework.beans.factory.annotation.Autowired

@Mapper(componentModel = "spring")
abstract class ItemBookingMapper : CRUDMapper<ItemBookingEntity, CreateItemBookingRequest, GetItemBookingResponse, PatchItemBookingRequest> {

    @Autowired
    protected lateinit var userService: UserService

    @Autowired
    protected lateinit var itemService: ItemService

    override fun create(request: CreateItemBookingRequest) = ItemBookingEntity(
        start = request.start,
        end = request.end,
        item = itemService.get(request.itemId),
        user = userService.get(request.userId),
        status = request.status
    )

    override fun get(entity: ItemBookingEntity) = GetItemBookingResponse(
        id = entity.id,
        start = entity.start,
        end = entity.end,
        itemId = entity.item.id,
        userId = entity.user.id,
        status = entity.status,
        updatedAt = entity.updatedAt,
        createdAt = entity.createdAt
    )

    override fun patch(
        entity: ItemBookingEntity,
        request: PatchItemBookingRequest
    ): ItemBookingEntity {
        request.start?.let { entity.start = it }
        request.end?.let { entity.end = it }
        request.itemId?.let { entity.item = itemService.get(it) }
        request.userId?.let { entity.user = userService.get(it) }
        request.status?.let { entity.status = it }
        return entity
    }

}