package de.csw.turtle.api.service

import de.csw.turtle.api.dto.create.CreateDeviceRequest
import de.csw.turtle.api.dto.get.GetDeviceResponse
import de.csw.turtle.api.dto.patch.PatchDeviceRequest
import de.csw.turtle.api.entity.DeviceEntity
import de.csw.turtle.api.exception.exceptions.device.DeviceNotFoundException
import de.csw.turtle.api.mapper.DeviceMapper
import de.csw.turtle.api.repository.DeviceRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
class DeviceService(
    override val repository: DeviceRepository,
    override val mapper: DeviceMapper
) : CRUDService<DeviceEntity, CreateDeviceRequest, GetDeviceResponse, PatchDeviceRequest, DeviceRepository, DeviceMapper>()