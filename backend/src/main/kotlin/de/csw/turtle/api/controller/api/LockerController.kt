package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Permission
import de.csw.turtle.api.Permission.*
import de.csw.turtle.api.controller.CRUDCreateController
import de.csw.turtle.api.controller.CRUDDeleteController
import de.csw.turtle.api.controller.CRUDGetController
import de.csw.turtle.api.controller.CRUDPatchController
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
@RequestMapping("/api/lockers")
class LockerController(
    override val endpoint: String = "/api/lockers",

    override val permissionCreate: Permission = BACKEND__API_LOCKERS__CREATE,
    override val permissionGet: Permission = BACKEND__API_LOCKERS__GET,
    override val permissionPatch: Permission = BACKEND__API_LOCKERS__PATCH,
    override val permissionDelete: Permission = BACKEND__API_LOCKERS__DELETE,

    override val service: LockerService,
    override val mapper: LockerMapper,
    override val securityService: SecurityService
) : CRUDCreateController<LockerEntity, CreateLockerRequest, GetLockerResponse>,
    CRUDGetController<LockerEntity, GetLockerResponse>,
    CRUDPatchController<LockerEntity, PatchLockerRequest, GetLockerResponse>,
    CRUDDeleteController<LockerEntity>