package de.csw.turtle.api.controller.api

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
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/roles")
class RoleController(
    override val endpoint: String = "/api/roles",
    override val service: RoleService,
    override val mapper: RoleMapper
) : CreateController<RoleEntity, CreateRoleRequest, GetRoleResponse>,
    GetController<RoleEntity, GetRoleResponse>,
    PatchController<RoleEntity, PatchRoleRequest, GetRoleResponse>,
    DeleteController<RoleEntity> {

    @PreAuthorize("hasAuthority('MANAGE_ROLES')")
    override fun create(request: CreateRoleRequest) = super.create(request)

    @PreAuthorize("hasAuthority('MANAGE_ROLES')")
    override fun patch(id: Long, request: PatchRoleRequest) = super.patch(id, request)

    @PreAuthorize("hasAuthority('MANAGE_ROLES')")
    override fun delete(id: Long) = super.delete(id)

}