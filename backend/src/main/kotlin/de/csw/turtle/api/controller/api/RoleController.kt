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
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.ForbiddenException
import de.csw.turtle.api.exception.UnauthorizedException
import de.csw.turtle.api.mapper.RoleMapper
import de.csw.turtle.api.service.RoleService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/roles")
class RoleController(
    private val roleService: RoleService,
    private val roleMapper: RoleMapper,
) : CreateController<RoleEntity, CreateRoleRequest, GetRoleResponse>,
    GetController<RoleEntity, GetRoleResponse>,
    PatchController<RoleEntity, PatchRoleRequest, GetRoleResponse>,
    DeleteController<RoleEntity> {

    override fun create(
        user: UserEntity?,
        request: CreateRoleRequest
    ): ResponseEntity<GetRoleResponse> {
        if (user == null)
            throw UnauthorizedException()

        if (!user.hasPermission(Permission.MANAGE_ROLES))
            throw ForbiddenException()

        val entity = roleService.create(request)
        val location = URI.create("/api/roles/${entity.id}")
        val dto = roleMapper.get(entity)
        return ResponseEntity.created(location).body(dto)
    }

    override fun get(
        user: UserEntity?,
        id: Long
    ): ResponseEntity<GetRoleResponse> {
        if (user == null)
            throw UnauthorizedException()

        val entity = roleService.get(id)
        if (!user.roles.contains(entity))
            throw ForbiddenException()

        val dto = roleMapper.get(entity)
        return ResponseEntity.ok(dto)
    }

    override fun getCollection(
        user: UserEntity?,
        rsql: String?,
        pageNumber: Int?,
        pageSize: Int,
        sortProperty: String?,
        sortDirection: Sort.Direction
    ): ResponseEntity<Any> {
        if (user == null)
            throw UnauthorizedException()

        val sort = sortProperty?.let {
            Sort.by(sortDirection, sortProperty)
        } ?: Sort.unsorted()

        if (pageNumber != null) {
            val pageable = PageRequest.of(pageNumber, pageSize, sort)
            val page = roleService.getPage(rsql = rsql, pageable = pageable)

            if(!user.hasPermission(Permission.MANAGE_ROLES))
                page.removeAll { !user.roles.contains(it) }

            val dto = page.map { roleMapper.get(it) }
            return ResponseEntity.ok(dto)
        }

        val collection = roleService.getAll(rsql = rsql, sort = sort).toMutableSet()

        if(!user.hasPermission(Permission.MANAGE_ROLES))
            collection.removeAll { !user.roles.contains(it) }

        val dto = collection.map { roleMapper.get(it) }
        return ResponseEntity.ok(dto)
    }

    override fun patch(
        user: UserEntity?,
        id: Long,
        request: PatchRoleRequest
    ): ResponseEntity<GetRoleResponse> {
        if (user == null)
            throw UnauthorizedException()

        if (!user.hasPermission(Permission.MANAGE_DEVICES))
            throw ForbiddenException()

        val entity = roleService.patch(id, request)
        val dto = roleMapper.get(entity)
        return ResponseEntity.ok(dto)
    }

    override fun delete(
        user: UserEntity?,
        id: Long
    ): ResponseEntity<Void> {
        if (user == null)
            throw UnauthorizedException()

        if (!user.hasPermission(Permission.MANAGE_DEVICES))
            throw ForbiddenException()

        roleService.delete(id)
        return ResponseEntity.noContent().build()
    }

}