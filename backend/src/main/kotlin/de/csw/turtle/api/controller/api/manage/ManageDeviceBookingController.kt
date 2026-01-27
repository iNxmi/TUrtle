package de.csw.turtle.api.controller.api.manage

import de.csw.turtle.api.Permission
import de.csw.turtle.api.Permission.*
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
import de.csw.turtle.api.service.PermissionService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/manage/device-bookings")
class ManageDeviceBookingController(
    override val endpoint: String = "/api/manage/device-bookings",

    override val permissionCreate: Permission = BACKEND__API_DEVICEBOOKINGS__CREATE,
    override val permissionGet: Permission = BACKEND__API_DEVICEBOOKINGS__GET,
    override val permissionPatch: Permission = BACKEND__API_DEVICEBOOKINGS__PATCH,
    override val permissionDelete: Permission = BACKEND__API_DEVICEBOOKINGS__DELETE,

    override val service: DeviceBookingService,
    override val mapper: DeviceBookingMapper,
    override val permissionService: PermissionService
) : CreateController<DeviceBookingEntity, CreateDeviceBookingRequest, GetDeviceBookingResponse>,
    GetController<DeviceBookingEntity, GetDeviceBookingResponse>,
    PatchController<DeviceBookingEntity, PatchDeviceBookingRequest, GetDeviceBookingResponse>,
    DeleteController<DeviceBookingEntity>