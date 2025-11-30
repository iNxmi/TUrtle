package de.csw.turtle.api.controller.crud

import de.csw.turtle.api.Permission
import de.csw.turtle.api.dto.create.CreateDeviceRequest
import de.csw.turtle.api.dto.get.GetDeviceResponse
import de.csw.turtle.api.dto.patch.PatchDeviceRequest
import de.csw.turtle.api.entity.DeviceEntity
import de.csw.turtle.api.mapper.DeviceMapper
import de.csw.turtle.api.service.DeviceService
import de.csw.turtle.api.service.SecurityService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/devices")
class DeviceController(
    override val endpoint: String = "/api/devices",

    override val createPermission: Permission = Permission.API_DEVICES__CREATE,
    override val getPermission: Permission = Permission.API_DEVICES__GET,
    override val patchPermission: Permission = Permission.API_DEVICES__PATCH,
    override val deletePermission: Permission = Permission.API_DEVICES__DELETE,

    override val service: DeviceService,
    override val mapper: DeviceMapper,
    override val securityService: SecurityService
) : CreateController<DeviceEntity, CreateDeviceRequest, GetDeviceResponse>,
    GetController<DeviceEntity, GetDeviceResponse>,
    PatchController<DeviceEntity, PatchDeviceRequest, GetDeviceResponse>,
    DeleteController<DeviceEntity>