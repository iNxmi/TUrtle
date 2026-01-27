package de.csw.turtle.api.controller.api.manage

import de.csw.turtle.api.Permission
import de.csw.turtle.api.Permission.BACKEND__API_ROLES__GET
import de.csw.turtle.api.Permission.BACKEND__API_ROLES__PATCH
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.controller.PatchController
import de.csw.turtle.api.dto.get.GetSystemSettingResponse
import de.csw.turtle.api.dto.patch.PatchSystemSettingRequest
import de.csw.turtle.api.entity.SystemSettingEntity
import de.csw.turtle.api.mapper.SystemSettingMapper
import de.csw.turtle.api.service.PermissionService
import de.csw.turtle.api.service.SystemSettingService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api/manage/system-settings")
class ManageSystemSettingController(
    override val permissionGet: Permission = BACKEND__API_ROLES__GET,
    override val permissionPatch: Permission = BACKEND__API_ROLES__PATCH,

    override val service: SystemSettingService,
    override val mapper: SystemSettingMapper,
    override val permissionService: PermissionService
) : GetController<SystemSettingEntity, GetSystemSettingResponse>,
    PatchController<SystemSettingEntity, PatchSystemSettingRequest, GetSystemSettingResponse>