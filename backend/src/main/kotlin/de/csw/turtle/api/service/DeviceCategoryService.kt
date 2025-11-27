package de.csw.turtle.api.service

import de.csw.turtle.api.dto.create.CreateDeviceCategoryRequest
import de.csw.turtle.api.dto.create.CreateDeviceRequest
import de.csw.turtle.api.dto.get.GetDeviceCategoryResponse
import de.csw.turtle.api.dto.patch.PatchDeviceCategoryRequest
import de.csw.turtle.api.dto.patch.PatchDeviceRequest
import de.csw.turtle.api.entity.DeviceCategoryEntity
import de.csw.turtle.api.entity.DeviceEntity
import de.csw.turtle.api.exception.exceptions.device.DeviceNotFoundException
import de.csw.turtle.api.exception.exceptions.device.category.DeviceCategoryNotFoundException
import de.csw.turtle.api.mapper.DeviceCategoryMapper
import de.csw.turtle.api.repository.DeviceCategoryRepository
import de.csw.turtle.api.repository.DeviceRepository
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import kotlin.jvm.optionals.getOrNull

@Service
class DeviceCategoryService(
    override val repository: DeviceCategoryRepository,
    override val mapper: DeviceCategoryMapper
) : CRUDService<DeviceCategoryEntity, CreateDeviceCategoryRequest, GetDeviceCategoryResponse, PatchDeviceCategoryRequest, DeviceCategoryRepository, DeviceCategoryMapper>()