package de.csw.turtle.api.controller.crud

import de.csw.turtle.api.dto.create.CreateDeviceRequest
import de.csw.turtle.api.dto.get.GetDeviceResponse
import de.csw.turtle.api.dto.patch.PatchDeviceRequest
import de.csw.turtle.api.entity.DeviceEntity
import de.csw.turtle.api.exception.exceptions.device.DeviceNotFoundException
import de.csw.turtle.api.mapper.DeviceMapper
import de.csw.turtle.api.repository.DeviceRepository
import de.csw.turtle.api.service.DeviceService
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort.Direction
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/api/devices")
class DeviceController(
    override val service: DeviceService,
    override val mapper: DeviceMapper
) : CRUDController<DeviceEntity, CreateDeviceRequest, GetDeviceResponse, PatchDeviceRequest, DeviceRepository, DeviceMapper, DeviceService>(
    "/api/devices"
)