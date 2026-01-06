package de.csw.turtle.api.service

import de.csw.turtle.api.dto.create.CreateDeviceBookingRequest
import de.csw.turtle.api.dto.get.GetDeviceBookingResponse
import de.csw.turtle.api.dto.patch.PatchDeviceBookingRequest
import de.csw.turtle.api.entity.DeviceBookingEntity
import de.csw.turtle.api.mapper.DeviceBookingMapper
import de.csw.turtle.api.repository.DeviceBookingRepository
import org.springframework.stereotype.Service

@Service
class DeviceBookingService(
    override val repository: DeviceBookingRepository,
    override val mapper: DeviceBookingMapper
): CRUDService<DeviceBookingEntity, CreateDeviceBookingRequest, GetDeviceBookingResponse, PatchDeviceBookingRequest>("DeviceBooking")