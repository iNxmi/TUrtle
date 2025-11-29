package de.csw.turtle.api.controller.crud

import de.csw.turtle.api.Permission
import de.csw.turtle.api.dto.create.CreateLockerRequest
import de.csw.turtle.api.dto.get.GetLockerResponse
import de.csw.turtle.api.dto.patch.PatchLockerRequest
import de.csw.turtle.api.entity.LockerEntity
import de.csw.turtle.api.mapper.LockerMapper
import de.csw.turtle.api.service.locker.LockerService
import de.csw.turtle.api.service.SecurityService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/resources/lockers")
class LockerController(
    override val endpoint: String = "/api/resources/lockers",

    override val createPermission: Permission = Permission.API_RESOURCES_LOCKERS__CREATE,
    override val getPermission: Permission = Permission.API_RESOURCES_LOCKERS__GET,
    override val patchPermission: Permission = Permission.API_RESOURCES_LOCKERS__PATCH,
    override val deletePermission: Permission = Permission.API_RESOURCES_LOCKERS__DELETE,

    override val service: LockerService,
    override val mapper: LockerMapper,
    override val securityService: SecurityService
) : CreateController<LockerEntity, CreateLockerRequest, GetLockerResponse>,
    GetController<LockerEntity, GetLockerResponse>,
    PatchController<LockerEntity, PatchLockerRequest, GetLockerResponse>,
    DeleteController<LockerEntity>