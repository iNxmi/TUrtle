package de.csw.turtle.api.controller.api

import de.csw.turtle.api.SimpleUserDetails
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
class UserController :
    CreateController<UserEntity, CreateUserRequest, GetUserResponse>,
    GetController<UserEntity, GetUserResponse>,
    PatchController<UserEntity, PatchUserRequest, GetUserResponse>,
    DeleteController<UserEntity> {

    override fun create(
        userDetails: SimpleUserDetails?,
        request: CreateUserRequest
    ): ResponseEntity<GetUserResponse> {
        TODO("Not yet implemented")
    }

    override fun get(
        userDetails: SimpleUserDetails?,
        id: Long
    ): ResponseEntity<GetUserResponse> {
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
        request: PatchUserRequest
    ): ResponseEntity<GetUserResponse> {
        TODO("Not yet implemented")
    }

    override fun delete(
        userDetails: SimpleUserDetails?,
        id: Long
    ): ResponseEntity<Void> {
        TODO("Not yet implemented")
    }

}