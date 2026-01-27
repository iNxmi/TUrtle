package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Permission
import de.csw.turtle.api.controller.CreateController
import de.csw.turtle.api.controller.DeleteController
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.controller.PatchController
import de.csw.turtle.api.dto.create.CreateDeviceRequest
import de.csw.turtle.api.dto.get.GetDeviceResponse
import de.csw.turtle.api.dto.patch.PatchDeviceRequest
import de.csw.turtle.api.entity.DeviceEntity
import de.csw.turtle.api.mapper.DeviceMapper
import de.csw.turtle.api.service.DeviceService
import de.csw.turtle.api.service.PermissionService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/devices")
class DeviceController(
    override val endpoint: String = "/api/devices",

    override val permissionCreate: Permission = Permission.BACKEND__API_DEVICES__CREATE,
    override val permissionGet: Permission = Permission.BACKEND__API_DEVICES__GET,
    override val permissionPatch: Permission = Permission.BACKEND__API_DEVICES__PATCH,
    override val permissionDelete: Permission = Permission.BACKEND__API_DEVICES__DELETE,

    override val service: DeviceService,
    override val mapper: DeviceMapper,
    override val permissionService: PermissionService
) : CreateController<DeviceEntity, CreateDeviceRequest, GetDeviceResponse>,
    GetController<DeviceEntity, GetDeviceResponse>,
    PatchController<DeviceEntity, PatchDeviceRequest, GetDeviceResponse>,
    DeleteController<DeviceEntity>