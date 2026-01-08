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
    protected lateinit var roleService: RoleService

    override fun create(request: CreateUserRequest): UserEntity {
        val entity = UserEntity(
            username = request.username,
            firstName = request.firstName,
            lastName = request.lastName,
            email = request.email,
            password = request.password
        )

        val roles = request.roleIds.map { roleService.get(it) }.toSet()
        entity.roles.addAll(roles)

        return entity
    }

    override fun get(entity: UserEntity) = GetUserResponse(
        id = entity.id,
        username = entity.username,
        firstName = entity.firstName,
        lastName = entity.lastName,
        email = entity.email,
        roleIds = entity.roles.map { it.id }.toSet(),
        createdAt = entity.createdAt
    )

    override fun patch(
        entity: UserEntity,
        request: PatchUserRequest
    ): UserEntity {
        request.username?.let { entity.username = it }
        request.firstName?.let { entity.firstName = it }
        request.lastName?.let { entity.lastName = it }
        request.lastName?.let { entity.lastName = it }
        request.email?.let { entity.email = it }
        request.password?.let { entity.password = it }

        if (request.roleIds != null) {
            val roles = request.roleIds.map { roleService.get(it) }.toSet()

            entity.roles.clear()
            entity.roles.addAll(roles)
        }

        return entity
    }

}