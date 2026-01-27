package de.csw.turtle.api.controller.api.manage

import de.csw.turtle.api.Permission
import de.csw.turtle.api.Permission.*
import de.csw.turtle.api.controller.CreateController
import de.csw.turtle.api.controller.DeleteController
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.controller.PatchController
import de.csw.turtle.api.dto.create.CreateLockerRequest
import de.csw.turtle.api.dto.get.GetLockerResponse
import de.csw.turtle.api.dto.patch.PatchLockerRequest
import de.csw.turtle.api.entity.LockerEntity
import de.csw.turtle.api.mapper.LockerMapper
import de.csw.turtle.api.service.PermissionService
import de.csw.turtle.api.service.locker.LockerService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/manage/lockers")
class ManageLockerController(
    override val endpoint: String = "/api/manage/lockers",

    override val permissionCreate: Permission = BACKEND__API_LOCKERS__CREATE,
    override val permissionGet: Permission = BACKEND__API_LOCKERS__GET,
    override val permissionPatch: Permission = BACKEND__API_LOCKERS__PATCH,
    override val permissionDelete: Permission = BACKEND__API_LOCKERS__DELETE,

    override val service: LockerService,
    override val mapper: LockerMapper,
    override val permissionService: PermissionService
) : CreateController<LockerEntity, CreateLockerRequest, GetLockerResponse>,
    GetController<LockerEntity, GetLockerResponse>,
    PatchController<LockerEntity, PatchLockerRequest, GetLockerResponse>,
    DeleteController<LockerEntity>