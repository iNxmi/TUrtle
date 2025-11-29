package de.csw.turtle.api.mapper

import de.csw.turtle.api.dto.create.CreateUserRequest
import de.csw.turtle.api.dto.get.GetUserResponse
import de.csw.turtle.api.dto.patch.PatchUserRequest
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.service.RoleService
import org.mapstruct.Mapper
import org.springframework.beans.factory.annotation.Autowired

@Mapper(componentModel = "spring")
abstract class UserMapper : CRUDMapper<UserEntity, CreateUserRequest, GetUserResponse, PatchUserRequest> {

    @Autowired
    protected lateinit var roleMapper: RoleMapper

    @Autowired
    protected lateinit var roleService: RoleService

    override fun create(request: CreateUserRequest) = UserEntity(
        userName = request.username,
        firstName = request.firstName,
        lastName = request.lastName,
        email = request.email,
        studentId = request.studentId,
        passwordHash = request.password
    )

    override fun get(entity: UserEntity) = GetUserResponse(
        id = entity.id,
        username = entity.userName,
        firstName = entity.firstName,
        lastName = entity.lastName,
        email = entity.email,
        studentId = entity.studentId,
        roles = entity.roles.map { it.id }.toSet(),
        createdAt = entity.createdAt
    )

    override fun patch(
        entity: UserEntity,
        request: PatchUserRequest
    ): UserEntity {
        request.username?.let { entity.userName = it }
        request.firstName?.let { entity.firstName = it }
        request.lastName?.let { entity.lastName = it }
        request.lastName?.let { entity.lastName = it }
        request.email?.let { entity.email = it }
        request.studentId?.let { entity.studentId = it }
        request.password?.let { entity.passwordHash = it }

        if (request.roleIds != null) {
            val roles = request.roleIds.map { roleService.get(it) }.toSet()

            entity.roles.clear()
            entity.roles.addAll(roles)
        }

        return entity
    }

}