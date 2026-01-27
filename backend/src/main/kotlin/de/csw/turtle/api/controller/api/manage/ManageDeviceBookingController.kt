package de.csw.turtle.api.controller.api.manage

import de.csw.turtle.api.controller.CreateController
import de.csw.turtle.api.controller.DeleteController
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.controller.PatchController
import de.csw.turtle.api.dto.create.CreateDeviceBookingRequest
import de.csw.turtle.api.dto.get.GetDeviceBookingResponse
import de.csw.turtle.api.dto.patch.PatchDeviceBookingRequest
import de.csw.turtle.api.entity.DeviceBookingEntity
import de.csw.turtle.api.mapper.DeviceBookingMapper
import de.csw.turtle.api.service.DeviceBookingService
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/manage/device-bookings")
class ManageDeviceBookingController(
    override val endpoint: String = "/api/manage/device-bookings",
    override val service: DeviceBookingService,
    override val mapper: DeviceBookingMapper
) : CreateController<DeviceBookingEntity, CreateDeviceBookingRequest, GetDeviceBookingResponse>,
    GetController<DeviceBookingEntity, GetDeviceBookingResponse>,
    PatchController<DeviceBookingEntity, PatchDeviceBookingRequest, GetDeviceBookingResponse>,
    DeleteController<DeviceBookingEntity> {

    @PreAuthorize("hasAuthority('MANAGE_DEVICE_BOOKINGS')")
    override fun create(request: CreateDeviceBookingRequest) = super.create(request)

    @PreAuthorize("hasAuthority('MANAGE_DEVICE_BOOKINGS')")
    override fun get(id: Long) = super.get(id)

    @PreAuthorize("hasAuthority('MANAGE_DEVICE_BOOKINGS')")
    override fun getCollection(
        rsql: String?,
        pageNumber: Int?,
        pageSize: Int,
        sortProperty: String?,
        sortDirection: Sort.Direction
    ): ResponseEntity<Any> = super.getCollection(rsql, pageNumber, pageSize, sortProperty, sortDirection)

    @PreAuthorize("hasAuthority('MANAGE_DEVICE_BOOKINGS')")
    override fun patch(id: Long, request: PatchDeviceBookingRequest) = super.patch(id, request)

    @PreAuthorize("hasAuthority('MANAGE_DEVICE_BOOKINGS')")
    override fun delete(id: Long) = super.delete(id)

}