package de.csw.turtle.api.controller.crud

import de.csw.turtle.api.dto.create.CreateUserRequest
import de.csw.turtle.api.dto.get.GetUserResponse
import de.csw.turtle.api.dto.patch.PatchUserRequest
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.mapper.UserMapper
import de.csw.turtle.api.repository.UserRepository
import de.csw.turtle.api.service.UserService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/users")
class UserController(
    override val service: UserService,
    override val mapper: UserMapper
) : CRUDController<UserEntity, CreateUserRequest, GetUserResponse, PatchUserRequest, UserRepository, UserMapper, UserService>(
    "/api/users",
    "api.users"
)