package de.csw.turtle.api.controller.api

import de.csw.turtle.api.controller.CreateController
import de.csw.turtle.api.controller.DeleteController
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.controller.PatchController
import de.csw.turtle.api.dto.create.CreateUserRequest
import de.csw.turtle.api.dto.get.GetUserResponse
import de.csw.turtle.api.dto.patch.PatchUserRequest
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.mapper.UserMapper
import de.csw.turtle.api.service.UserService
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(
    override val endpoint: String = "/api/users",
    override val service: UserService,
    override val mapper: UserMapper
) : CreateController<UserEntity, CreateUserRequest, GetUserResponse>,
    GetController<UserEntity, GetUserResponse>,
    PatchController<UserEntity, PatchUserRequest, GetUserResponse>,
    DeleteController<UserEntity> {

    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    override fun create(request: CreateUserRequest) = super.create(request)

    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    override fun get(id: Long) = super.get(id)

    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    override fun getCollection(
        rsql: String?,
        pageNumber: Int?,
        pageSize: Int,
        sortProperty: String?,
        sortDirection: Sort.Direction
    ): ResponseEntity<Any> = super.getCollection(rsql, pageNumber, pageSize, sortProperty, sortDirection)

    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    override fun patch(id: Long, request: PatchUserRequest) = super.patch(id, request)

    @PreAuthorize("hasAuthority('MANAGE_USERS')")
    override fun delete(id: Long) = super.delete(id)

}