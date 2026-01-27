package de.csw.turtle.api.controller.api

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
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/devices")
class DeviceController(
    override val endpoint: String = "/api/devices",
    override val service: DeviceService,
    override val mapper: DeviceMapper
) : CreateController<DeviceEntity, CreateDeviceRequest, GetDeviceResponse>,
    GetController<DeviceEntity, GetDeviceResponse>,
    PatchController<DeviceEntity, PatchDeviceRequest, GetDeviceResponse>,
    DeleteController<DeviceEntity>