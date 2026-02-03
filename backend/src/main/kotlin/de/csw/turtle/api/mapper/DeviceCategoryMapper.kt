package de.csw.turtle.api.mapper

import de.csw.turtle.api.dto.create.CreateDeviceCategoryRequest
import de.csw.turtle.api.dto.get.GetDeviceCategoryResponse
import de.csw.turtle.api.dto.patch.PatchDeviceCategoryRequest
import de.csw.turtle.api.entity.DeviceCategoryEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
abstract class DeviceCategoryMapper :
    CRUDMapper<DeviceCategoryEntity, CreateDeviceCategoryRequest, GetDeviceCategoryResponse, PatchDeviceCategoryRequest> {

    override fun create(request: CreateDeviceCategoryRequest) = DeviceCategoryEntity(
        name = request.name
    )

    override fun get(entity: DeviceCategoryEntity) = GetDeviceCategoryResponse(
        id = entity.id,
        name = entity.name,
        updatedAt = entity.updatedAt,
        createdAt = entity.createdAt
    )

    override fun patch(
        entity: DeviceCategoryEntity,
        request: PatchDeviceCategoryRequest
    ): DeviceCategoryEntity {
        request.name?.let { entity.name = it }
        return entity
    }

}