package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Permission
import de.csw.turtle.api.Permission.*
import de.csw.turtle.api.controller.CreateController
import de.csw.turtle.api.controller.DeleteController
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.controller.PatchController
import de.csw.turtle.api.dto.create.CreateUserRequest
import de.csw.turtle.api.dto.get.GetUserResponse
import de.csw.turtle.api.dto.patch.PatchUserRequest
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.mapper.UserMapper
import de.csw.turtle.api.service.SecurityService
import de.csw.turtle.api.service.UserService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(
    override val endpoint: String = "/api/users",

    override val createPermission: Permission = BACKEND__API_USERS__CREATE,
    override val getPermission: Permission = BACKEND__API_USERS__GET,
    override val patchPermission: Permission = BACKEND__API_USERS__PATCH,
    override val deletePermission: Permission = BACKEND__API_USERS__DELETE,

    override val service: UserService,
    override val mapper: UserMapper,
    override val securityService: SecurityService
) : CreateController<UserEntity, CreateUserRequest, GetUserResponse>,
    GetController<UserEntity, GetUserResponse>,
    PatchController<UserEntity, PatchUserRequest, GetUserResponse>,
    DeleteController<UserEntity>