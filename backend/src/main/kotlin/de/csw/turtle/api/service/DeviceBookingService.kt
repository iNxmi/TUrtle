package de.csw.turtle.api.service

import de.csw.turtle.api.dto.create.CreateDeviceBookingRequest
import de.csw.turtle.api.dto.get.GetDeviceBookingResponse
import de.csw.turtle.api.dto.patch.PatchDeviceBookingRequest
import de.csw.turtle.api.entity.DeviceBookingEntity
import de.csw.turtle.api.entity.RoomBookingEntity
import de.csw.turtle.api.mapper.DeviceBookingMapper
import de.csw.turtle.api.repository.DeviceBookingRepository
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class DeviceBookingService(
    override val repository: DeviceBookingRepository,
    override val mapper: DeviceBookingMapper,
    private val deviceService: DeviceService
): CRUDService<DeviceBookingEntity, CreateDeviceBookingRequest, GetDeviceBookingResponse, PatchDeviceBookingRequest>("DeviceBooking"){



    fun getAllOverlapping(start: Instant, end: Instant,device: Long ): Set<DeviceBookingEntity> =
        repository.findAllOverlapping(start, end, deviceService.get(device))

}