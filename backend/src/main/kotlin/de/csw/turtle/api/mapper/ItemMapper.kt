package de.csw.turtle.api.mapper

import de.csw.turtle.api.dto.create.CreateItemRequest
import de.csw.turtle.api.dto.get.GetItemResponse
import de.csw.turtle.api.dto.patch.PatchItemRequest
import de.csw.turtle.api.entity.ItemEntity
import de.csw.turtle.api.service.ItemCategoryService
import de.csw.turtle.api.service.locker.LockerService
import org.mapstruct.Mapper
import org.springframework.beans.factory.annotation.Autowired

@Mapper(componentModel = "spring")
abstract class ItemMapper : CRUDMapper<ItemEntity, CreateItemRequest, GetItemResponse, PatchItemRequest> {

    @Autowired
    protected lateinit var itemCategoryService: ItemCategoryService

    @Autowired
    protected lateinit var lockerService: LockerService

    override fun create(request: CreateItemRequest) = ItemEntity(
        name = request.name,
        description = request.description,
        category = itemCategoryService.get(request.categoryId),
        locker = lockerService.get(request.lockerId),
        acquiredAt = request.acquiredAt
    )

    override fun get(entity: ItemEntity) = GetItemResponse(
        id = entity.id,
        name = entity.name,
        description = entity.description,
        category = entity.category.id,
        locker = entity.locker.id,
        acquiredAt = entity.acquiredAt,
        updatedAt = entity.updatedAt,
        createdAt = entity.createdAt
    )

    override fun patch(
        entity: ItemEntity,
        request: PatchItemRequest
    ): ItemEntity {
        request.name?.let { entity.name = it }
        request.description?.let { entity.description = it }
        request.categoryId?.let { entity.category = itemCategoryService.get(it) }
        request.lockerId?.let { entity.locker = lockerService.get(it) }
        request.acquiredAt?.let { entity.acquiredAt = it }
        return entity
    }

}