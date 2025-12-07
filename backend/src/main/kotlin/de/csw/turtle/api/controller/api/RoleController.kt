package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Permission
import de.csw.turtle.api.Permission.*
import de.csw.turtle.api.controller.CRUDCreateController
import de.csw.turtle.api.controller.CRUDDeleteController
import de.csw.turtle.api.controller.CRUDGetController
import de.csw.turtle.api.controller.CRUDPatchController
import de.csw.turtle.api.dto.create.CreateRoleRequest
import de.csw.turtle.api.dto.get.GetRoleResponse
import de.csw.turtle.api.dto.patch.PatchRoleRequest
import de.csw.turtle.api.entity.RoleEntity
import de.csw.turtle.api.mapper.RoleMapper
import de.csw.turtle.api.service.RoleService
import de.csw.turtle.api.service.PermissionService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/roles")
class RoleController(
    override val endpoint: String = "/api/roles",

    override val permissionCreate: Permission = BACKEND__API_ROLES__CREATE,
    override val permissionGet: Permission = BACKEND__API_ROLES__GET,
    override val permissionPatch: Permission = BACKEND__API_ROLES__PATCH,
    override val permissionDelete: Permission = BACKEND__API_ROLES__DELETE,

    override val service: RoleService,
    override val mapper: RoleMapper,
    override val permissionService: PermissionService
) : CRUDCreateController<RoleEntity, CreateRoleRequest, GetRoleResponse>,
    CRUDGetController<RoleEntity, GetRoleResponse>,
    CRUDPatchController<RoleEntity, PatchRoleRequest, GetRoleResponse>,
    CRUDDeleteController<RoleEntity>