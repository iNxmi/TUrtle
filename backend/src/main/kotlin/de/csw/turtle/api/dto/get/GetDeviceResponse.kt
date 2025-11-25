package de.csw.turtle.api.dto.get

import de.csw.turtle.api.entity.DeviceEntity

data class GetDeviceResponse(
    val id: Long,
    val name: String,
    val description: String,
    val inventoryId: String,
    val category: GetDeviceCategoryResponse,
    val locker: GetLockerResponse
) {

    constructor(entity: DeviceEntity) : this(
        id = entity.id,
        name = entity.name,
        description = entity.description,
        inventoryId = entity.inventoryId,
        category = GetDeviceCategoryResponse(entity.category),
        locker = GetLockerResponse(entity.locker)
    )

}