package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Permission
import de.csw.turtle.api.controller.CreateController
import de.csw.turtle.api.controller.DeleteController
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.controller.PatchController
import de.csw.turtle.api.dto.create.CreateUserRequest
import de.csw.turtle.api.dto.get.GetUserResponse
import de.csw.turtle.api.dto.patch.PatchUserRequest
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.mapper.UserMapper
import de.csw.turtle.api.service.RoleService
import de.csw.turtle.api.service.UserService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService,
    private val userMapper: UserMapper,
    private val roleService: RoleService,
) : CreateController<UserEntity, CreateUserRequest, GetUserResponse>,
    GetController<UserEntity, GetUserResponse>,
    PatchController<UserEntity, PatchUserRequest, GetUserResponse>,
    DeleteController<UserEntity> {

    override fun create(
        user: UserEntity?,
        request: CreateUserRequest
    ): ResponseEntity<GetUserResponse> {
        val sanitized = if (user == null) {
            request.copy(roleIds = setOf(roleService.getByName("Student").id))
        } else if (user.hasPermission(Permission.MANAGE_USERS)) {
            request
        } else throw HttpException.Forbidden()

        val entity = userService.create(sanitized)
        val location = URI.create("/api/users/${entity.id}")
        val dto = userMapper.get(entity)
        return ResponseEntity.created(location).body(dto)
    }

    override fun get(
        user: UserEntity?,
        id: Long
    ): ResponseEntity<GetUserResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        val entity = userService.get(id)
        if (!user.hasPermission(Permission.MANAGE_USERS))
            if (entity.id != user.id)
                throw HttpException.Forbidden()

        val dto = userMapper.get(entity)
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
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_USERS))
            throw HttpException.Forbidden()

        val sort = sortProperty?.let {
            Sort.by(sortDirection, sortProperty)
        } ?: Sort.unsorted()

        if (pageNumber != null) {
            val pageable = PageRequest.of(pageNumber, pageSize, sort)
            val page = userService.getPage(rsql = rsql, pageable = pageable)
            val dto = page.map { userMapper.get(it) }
            return ResponseEntity.ok(dto)
        }

        val collection = userService.getAll(rsql = rsql, sort = sort).toMutableSet()
        val dto = collection.map { userMapper.get(it) }
        return ResponseEntity.ok(dto)
    }

    override fun patch(
        user: UserEntity?,
        id: Long,
        request: PatchUserRequest
    ): ResponseEntity<GetUserResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        val sanitized = if (id == user.id) {
            request.copy(roleIds = null)
        } else if (user.hasPermission(Permission.MANAGE_USERS)) {
            request
        } else throw HttpException.Forbidden()

        val entity = userService.patch(id, sanitized)
        val dto = userMapper.get(entity)
        return ResponseEntity.ok(dto)
    }

    override fun delete(
        user: UserEntity?,
        id: Long
    ): ResponseEntity<Void> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_USERS))
            if (id != user.id)
                throw HttpException.Forbidden()

        userService.delete(id)
        return ResponseEntity.noContent().build()
    }

}