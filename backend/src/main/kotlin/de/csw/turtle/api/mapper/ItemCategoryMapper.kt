package de.csw.turtle.api.mapper

import de.csw.turtle.api.dto.create.CreateItemCategoryRequest
import de.csw.turtle.api.dto.get.GetItemCategoryResponse
import de.csw.turtle.api.dto.patch.PatchItemCategoryRequest
import de.csw.turtle.api.entity.ItemCategoryEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
abstract class ItemCategoryMapper :
    CRUDMapper<ItemCategoryEntity, CreateItemCategoryRequest, GetItemCategoryResponse, PatchItemCategoryRequest> {

    override fun create(request: CreateItemCategoryRequest) = ItemCategoryEntity(
        name = request.name
    )

    override fun get(entity: ItemCategoryEntity) = GetItemCategoryResponse(
        id = entity.id,
        name = entity.name,
        updatedAt = entity.updatedAt,
        createdAt = entity.createdAt
    )

    override fun patch(
        entity: ItemCategoryEntity,
        request: PatchItemCategoryRequest
    ): ItemCategoryEntity {
        request.name?.let { entity.name = it }
        return entity
    }

}