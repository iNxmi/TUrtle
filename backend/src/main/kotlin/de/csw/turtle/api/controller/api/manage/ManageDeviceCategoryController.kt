package de.csw.turtle.api.controller.api.manage

import de.csw.turtle.api.Permission
import de.csw.turtle.api.Permission.*
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
import de.csw.turtle.api.service.PermissionService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/manage/device-categories")
class ManageDeviceCategoryController(
    override val endpoint: String = "/api/manage/device-categories",

    override val permissionCreate: Permission = BACKEND__API_DEVICECATEGORIES__CREATE,
    override val permissionGet: Permission = BACKEND__API_DEVICECATEGORIES__GET,
    override val permissionPatch: Permission = BACKEND__API_DEVICECATEGORIES__PATCH,
    override val permissionDelete: Permission = BACKEND__API_DEVICECATEGORIES__DELETE,

    override val service: DeviceCategoryService,
    override val mapper: DeviceCategoryMapper,
    override val permissionService: PermissionService
) : CreateController<DeviceCategoryEntity, CreateDeviceCategoryRequest, GetDeviceCategoryResponse>,
    GetController<DeviceCategoryEntity, GetDeviceCategoryResponse>,
    PatchController<DeviceCategoryEntity, PatchDeviceCategoryRequest, GetDeviceCategoryResponse>,
    DeleteController<DeviceCategoryEntity>