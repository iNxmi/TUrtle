package de.csw.turtle.api.controller.api

import de.csw.turtle.api.controller.CreateController
import de.csw.turtle.api.controller.DeleteController
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.controller.PatchController
import de.csw.turtle.api.dto.create.CreateDeviceCategoryRequest
import de.csw.turtle.api.dto.get.GetDeviceCategoryResponse
import de.csw.turtle.api.dto.patch.PatchDeviceCategoryRequest
import de.csw.turtle.api.entity.DeviceCategoryEntity
import de.csw.turtle.api.mapper.DeviceCategoryMapper
import de.csw.turtle.api.service.DeviceCategoryService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/device-categories")
class DeviceCategoryController(
    override val endpoint: String = "/api/device-categories",
    override val service: DeviceCategoryService,
    override val mapper: DeviceCategoryMapper
) : CreateController<DeviceCategoryEntity, CreateDeviceCategoryRequest, GetDeviceCategoryResponse>,
    GetController<DeviceCategoryEntity, GetDeviceCategoryResponse>,
    PatchController<DeviceCategoryEntity, PatchDeviceCategoryRequest, GetDeviceCategoryResponse>,
    DeleteController<DeviceCategoryEntity> {

    @PreAuthorize("hasAuthority('MANAGE_DEVICES')")
    override fun create(request: CreateDeviceCategoryRequest) = super.create(request)

    @PreAuthorize("hasAuthority('MANAGE_DEVICES')")
    override fun patch(id: Long, request: PatchDeviceCategoryRequest) = super.patch(id, request)

    @PreAuthorize("hasAuthority('MANAGE_DEVICES')")
    override fun delete(id: Long) = super.delete(id)

}