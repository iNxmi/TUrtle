package de.csw.turtle.api.controller.crud

import de.csw.turtle.api.dto.create.CreateRoleRequest
import de.csw.turtle.api.dto.get.GetRoleResponse
import de.csw.turtle.api.dto.patch.PatchRoleRequest
import de.csw.turtle.api.entity.RoleEntity
import de.csw.turtle.api.mapper.RoleMapper
import de.csw.turtle.api.repository.RoleRepository
import de.csw.turtle.api.service.RoleService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/resources/roles")
class RoleController(
    override val service: RoleService,
    override val mapper: RoleMapper
) : CRUDController<RoleEntity, CreateRoleRequest, GetRoleResponse, PatchRoleRequest, RoleRepository, RoleMapper, RoleService>(
    "/api/resources/roles"
)