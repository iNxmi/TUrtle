package de.csw.turtle.api.dto.get

import de.csw.turtle.api.entity.DeviceEntity
import de.csw.turtle.api.entity.LockerEntity

data class GetLockerResponse(
    val id: Long,
    val index: Int,
    val name: String,
    val devices: MutableSet<DeviceEntity> = mutableSetOf(),
) {

    constructor(locker: LockerEntity) : this(
        id = locker.id,
        index = locker.index,
        name = locker.name,
        devices = locker.devices.toMutableSet(),
    )

}