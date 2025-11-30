package de.csw.turtle.api.controller.crud

import de.csw.turtle.api.Permission
import de.csw.turtle.api.dto.create.CreateDeviceCategoryRequest
import de.csw.turtle.api.dto.get.GetDeviceCategoryResponse
import de.csw.turtle.api.dto.patch.PatchDeviceCategoryRequest
import de.csw.turtle.api.entity.DeviceCategoryEntity
import de.csw.turtle.api.mapper.DeviceCategoryMapper
import de.csw.turtle.api.service.DeviceCategoryService
import de.csw.turtle.api.service.SecurityService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/devicecategories")
class DeviceCategoryController(
    override val endpoint: String = "/api/devicecategories",

    override val createPermission: Permission = Permission.API_DEVICECATEGORIES__CREATE,
    override val getPermission: Permission = Permission.API_DEVICECATEGORIES__GET,
    override val patchPermission: Permission = Permission.API_DEVICECATEGORIES__PATCH,
    override val deletePermission: Permission = Permission.API_DEVICECATEGORIES__DELETE,

    override val service: DeviceCategoryService,
    override val mapper: DeviceCategoryMapper,
    override val securityService: SecurityService
) : CreateController<DeviceCategoryEntity, CreateDeviceCategoryRequest, GetDeviceCategoryResponse>,
    GetController<DeviceCategoryEntity, GetDeviceCategoryResponse>,
    PatchController<DeviceCategoryEntity, PatchDeviceCategoryRequest, GetDeviceCategoryResponse>,
    DeleteController<DeviceCategoryEntity>