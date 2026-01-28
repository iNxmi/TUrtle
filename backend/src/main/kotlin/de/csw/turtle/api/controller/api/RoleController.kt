package de.csw.turtle.api.controller.api

import de.csw.turtle.api.SimpleUserDetails
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
class RoleController :
    CreateController<RoleEntity, CreateRoleRequest, GetRoleResponse>,
    GetController<RoleEntity, GetRoleResponse>,
    PatchController<RoleEntity, PatchRoleRequest, GetRoleResponse>,
    DeleteController<RoleEntity> {

    override fun create(
        userDetails: SimpleUserDetails?,
        request: CreateRoleRequest
    ): ResponseEntity<GetRoleResponse> {
        TODO("Not yet implemented")
    }

    override fun get(
        userDetails: SimpleUserDetails?,
        id: Long
    ): ResponseEntity<GetRoleResponse> {
        TODO("Not yet implemented")
    }

    override fun getCollection(
        userDetails: SimpleUserDetails?,
        rsql: String?,
        pageNumber: Int?,
        pageSize: Int,
        sortProperty: String?,
        sortDirection: Sort.Direction
    ): ResponseEntity<Any> {
        TODO("Not yet implemented")
    }

    override fun patch(
        userDetails: SimpleUserDetails?,
        id: Long,
        request: PatchRoleRequest
    ): ResponseEntity<GetRoleResponse> {
        TODO("Not yet implemented")
    }

    override fun delete(
        userDetails: SimpleUserDetails?,
        id: Long
    ): ResponseEntity<Void> {
        TODO("Not yet implemented")
    }

}