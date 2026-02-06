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

private const val ENDPOINT = "/api/roles"

@RestController
@RequestMapping(ENDPOINT)
class RoleController(
    private val roleService: RoleService
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

        if (roleService.getByNameOrNull(request.name) != null)
            throw HttpException.Conflict("Role with name '${request.name}' already exists.")

        val entity = roleService.create(
            name = request.name,
            permissions = request.permissions
        )

        val location = URI.create("$ENDPOINT/${entity.id}")
        val dto = GetRoleResponse(entity)
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

        val entity = roleService.getById(variable)
            ?: throw HttpException.NotFound()

        if (!user.roles.contains(entity))
            throw HttpException.Forbidden()

        val dto = GetRoleResponse(entity)
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
            val dto = page.map { GetRoleResponse(it) }
            return ResponseEntity.ok(dto)
        }

        val collection = roleService.getAll(rsql = rsql, sort = sort, specification = specification).toMutableSet()
        val dto = collection.map { GetRoleResponse(it) }
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

        if (request.name != null)
            if (roleService.getByNameOrNull(request.name) != null)
                throw HttpException.Conflict("Role with name '${request.name}' already exists.")

        val entity = roleService.patch(
            id = id,
            name = request.name,
            permissions = request.permissions
        )

        val dto = GetRoleResponse(entity)
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