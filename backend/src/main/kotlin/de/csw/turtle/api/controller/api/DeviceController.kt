package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Permission
import de.csw.turtle.api.Permission.*
import de.csw.turtle.api.controller.CRUDCreateController
import de.csw.turtle.api.controller.CRUDDeleteController
import de.csw.turtle.api.controller.CRUDGetController
import de.csw.turtle.api.controller.CRUDPatchController
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

    override val createPermission: Permission = BACKEND__API_DEVICES__CREATE,
    override val getPermission: Permission = BACKEND__API_DEVICES__GET,
    override val patchPermission: Permission = BACKEND__API_DEVICES__PATCH,
    override val deletePermission: Permission = BACKEND__API_DEVICES__DELETE,

    override val service: DeviceService,
    override val mapper: DeviceMapper,
    override val securityService: SecurityService
) : CRUDCreateController<DeviceEntity, CreateDeviceRequest, GetDeviceResponse>,
    CRUDGetController<DeviceEntity, GetDeviceResponse>,
    CRUDPatchController<DeviceEntity, PatchDeviceRequest, GetDeviceResponse>,
    CRUDDeleteController<DeviceEntity>