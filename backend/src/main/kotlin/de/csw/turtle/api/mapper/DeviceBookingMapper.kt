package de.csw.turtle.api.mapper

import de.csw.turtle.api.dto.create.CreateDeviceBookingRequest
import de.csw.turtle.api.dto.get.GetDeviceBookingResponse
import de.csw.turtle.api.dto.patch.PatchDeviceBookingRequest
import de.csw.turtle.api.entity.DeviceBookingEntity
import de.csw.turtle.api.service.DeviceService
import de.csw.turtle.api.service.UserService
import org.mapstruct.Mapper
import org.springframework.beans.factory.annotation.Autowired

@Mapper(componentModel = "spring")
abstract class DeviceBookingMapper : CRUDMapper<DeviceBookingEntity, CreateDeviceBookingRequest, GetDeviceBookingResponse, PatchDeviceBookingRequest> {

    @Autowired
    protected lateinit var userService: UserService

    @Autowired
    protected lateinit var deviceService: DeviceService

    override fun create(request: CreateDeviceBookingRequest) = DeviceBookingEntity(
        start = request.start,
        end = request.end,
        device = deviceService.get(request.deviceId),
        user = userService.get(request.userId)
    )

    override fun get(entity: DeviceBookingEntity) = GetDeviceBookingResponse(
        id = entity.id,
        start = entity.start,
        end = entity.end,
        deviceId = entity.device.id,
        userId = entity.user.id,
        createdAt = entity.createdAt,
        status = entity.status
    )

    override fun patch(
        entity: DeviceBookingEntity,
        request: PatchDeviceBookingRequest
    ): DeviceBookingEntity {
        request.start?.let { entity.start = it }
        request.end?.let { entity.end = it }
        request.deviceId?.let { entity.device = deviceService.get(it) }
        request.userId?.let { entity.user = userService.get(it) }
        request.status?.let { entity.status = it }
        return entity
    }

}