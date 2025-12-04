package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Permission
import de.csw.turtle.api.controller.CreateController
import de.csw.turtle.api.controller.DeleteController
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.controller.PatchController
import de.csw.turtle.api.dto.create.CreateRoleRequest
import de.csw.turtle.api.dto.get.GetRoleResponse
import de.csw.turtle.api.dto.patch.PatchRoleRequest
import de.csw.turtle.api.entity.RoleEntity
import de.csw.turtle.api.mapper.RoleMapper
import de.csw.turtle.api.service.RoleService
import de.csw.turtle.api.service.SecurityService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/roles")
class RoleController(
    override val endpoint: String = "/api/roles",

    override val createPermission: Permission = Permission.BACKEND__API_ROLES__CREATE,
    override val getPermission: Permission = Permission.BACKEND__API_ROLES__GET,
    override val patchPermission: Permission = Permission.BACKEND__API_ROLES__PATCH,
    override val deletePermission: Permission = Permission.BACKEND__API_ROLES__DELETE,

    override val service: RoleService,
    override val mapper: RoleMapper,
    override val securityService: SecurityService
) : CreateController<RoleEntity, CreateRoleRequest, GetRoleResponse>,
    GetController<RoleEntity, GetRoleResponse>,
    PatchController<RoleEntity, PatchRoleRequest, GetRoleResponse>,
    DeleteController<RoleEntity>