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
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.mapper.RoleMapper
import de.csw.turtle.api.service.RoleService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
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
    GetController<RoleEntity, Long, GetRoleResponse>,
    PatchController<RoleEntity, PatchRoleRequest, GetRoleResponse>,
    DeleteController<RoleEntity> {

    override fun create(
        user: UserEntity?,

        request: CreateRoleRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetRoleResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_ROLES))
            throw HttpException.Forbidden()

        val entity = roleService.create(request)
        val location = URI.create("/api/roles/${entity.id}")
        val dto = roleMapper.get(entity)
        return ResponseEntity.created(location).body(dto)
    }

    override fun get(
        user: UserEntity?,

        variable: Long,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetRoleResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        val entity = roleService.get(variable)
        if (!user.roles.contains(entity))
            throw HttpException.Forbidden()

        val dto = roleMapper.get(entity)
        return ResponseEntity.ok(dto)
    }

    override fun getCollection(
        user: UserEntity?,

        rsql: String?,
        pageNumber: Int?,
        pageSize: Int,
        sortProperty: String?,
        sortDirection: Sort.Direction,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<Any> {
        if (user == null)
            throw HttpException.Unauthorized()

        val sort = sortProperty?.let {
            Sort.by(sortDirection, sortProperty)
        } ?: Sort.unsorted()

        val specification: Specification<RoleEntity> = if (user.hasPermission(Permission.MANAGE_ROLES)) {
            Specification.unrestricted()
        } else Specification { root, _, builder ->
            builder.isMember(user, root.get<Set<UserEntity>>("users"))
        }

        if (pageNumber != null) {
            val pageable = PageRequest.of(pageNumber, pageSize, sort)
            val page = roleService.getPage(rsql = rsql, pageable = pageable, specification = specification)
            val dto = page.map { roleMapper.get(it) }
            return ResponseEntity.ok(dto)
        }

        val collection = roleService.getAll(rsql = rsql, sort = sort, specification = specification).toMutableSet()
        val dto = collection.map { roleMapper.get(it) }
        return ResponseEntity.ok(dto)
    }

    override fun patch(
        user: UserEntity?,

        id: Long,
        request: PatchRoleRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetRoleResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_ROLES))
            throw HttpException.Forbidden()

        val entity = roleService.patch(id, request)
        val dto = roleMapper.get(entity)
        return ResponseEntity.ok(dto)
    }

    override fun delete(
        user: UserEntity?,

        id: Long,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<Void> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_ROLES))
            throw HttpException.Forbidden()

        roleService.delete(id)
        return ResponseEntity.noContent().build()
    }

}