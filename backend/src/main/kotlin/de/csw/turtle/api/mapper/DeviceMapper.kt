package de.csw.turtle.api.mapper

import de.csw.turtle.api.dto.create.CreateDeviceRequest
import de.csw.turtle.api.dto.get.GetDeviceResponse
import de.csw.turtle.api.dto.patch.PatchDeviceRequest
import de.csw.turtle.api.entity.DeviceEntity
import de.csw.turtle.api.service.DeviceCategoryService
import de.csw.turtle.api.service.LockerService
import org.mapstruct.Mapper
import org.springframework.beans.factory.annotation.Autowired

@Mapper(componentModel = "spring")
abstract class DeviceMapper: CRUDMapper<DeviceEntity, CreateDeviceRequest, GetDeviceResponse, PatchDeviceRequest> {

    @Autowired
    protected lateinit var deviceCategoryService: DeviceCategoryService

    @Autowired
    protected lateinit var lockerService: LockerService

    override fun create(request: CreateDeviceRequest) = DeviceEntity(
        name = request.name,
        description = request.description,
        inventoryId = request.inventoryId,
        category = deviceCategoryService.get(request.categoryId),
        locker = lockerService.get(request.lockerId)
    )

    override fun get(entity: DeviceEntity) = GetDeviceResponse(
        id = entity.id,
        name = entity.name,
        description = entity.description,
        inventoryId = entity.inventoryId,
        category = entity.category.id,
        locker = entity.locker.id,
        createdAt = entity.createdAt
    )

    override fun patch(
        entity: DeviceEntity,
        request: PatchDeviceRequest
    ): DeviceEntity {
        request.name?.let { entity.name = it }
        request.description?.let { entity.description = it }
        request.inventoryId?.let { entity.inventoryId = it }
        request.categoryId?.let { entity.category = deviceCategoryService.get(it) }
        request.lockerId?.let { entity.locker = lockerService.get(it) }
        return entity
    }

}