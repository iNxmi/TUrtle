package de.csw.turtle.api.service

import de.csw.turtle.api.dto.create.CreateDeviceCategoryRequest
import de.csw.turtle.api.dto.get.GetDeviceCategoryResponse
import de.csw.turtle.api.dto.patch.PatchDeviceCategoryRequest
import de.csw.turtle.api.entity.DeviceCategoryEntity
import de.csw.turtle.api.mapper.DeviceCategoryMapper
import de.csw.turtle.api.repository.DeviceCategoryRepository
import org.springframework.stereotype.Service

@Service
class DeviceCategoryService(
    override val repository: DeviceCategoryRepository,
    override val mapper: DeviceCategoryMapper
) : CRUDService<DeviceCategoryEntity, CreateDeviceCategoryRequest, GetDeviceCategoryResponse, PatchDeviceCategoryRequest>("DeviceCategory")