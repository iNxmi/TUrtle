package de.csw.turtle.api.controller.crud

import de.csw.turtle.api.dto.create.CreateDeviceCategoryRequest
import de.csw.turtle.api.dto.get.GetDeviceCategoryResponse
import de.csw.turtle.api.dto.patch.PatchDeviceCategoryRequest
import de.csw.turtle.api.entity.DeviceCategoryEntity
import de.csw.turtle.api.mapper.DeviceCategoryMapper
import de.csw.turtle.api.repository.DeviceCategoryRepository
import de.csw.turtle.api.service.DeviceCategoryService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/devicecategories")
class DeviceCategoryController(
    override val service: DeviceCategoryService,
    override val mapper: DeviceCategoryMapper
) : CRUDController<DeviceCategoryEntity, CreateDeviceCategoryRequest, GetDeviceCategoryResponse, PatchDeviceCategoryRequest, DeviceCategoryRepository, DeviceCategoryMapper, DeviceCategoryService>(
    "/api/devicecategories",
    "api.devicecategories"
)