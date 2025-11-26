package de.csw.turtle.api.mapper

import de.csw.turtle.api.dto.create.CreateUserRequest
import de.csw.turtle.api.dto.get.GetUserResponse
import de.csw.turtle.api.dto.patch.PatchUserRequest
import de.csw.turtle.api.entity.UserEntity
import org.mapstruct.Mapper

@Mapper
class UserMapper : CRUDMapper<UserEntity, CreateUserRequest, GetUserResponse, PatchUserRequest> {

    override fun get(entity: UserEntity) = GetUserResponse(
        id = entity.id,
        username = entity.userName,
        firstName = entity.firstName,
        lastName = entity.lastName,
        email = entity.email,
        studentId = entity.studentId,
        role = entity.role,
        createdAt = entity.createdAt
    )

    override fun create(request: CreateUserRequest) = UserEntity(
        userName = request.username,
        firstName = request.firstName,
        lastName = request.lastName,
        email = request.email,
        studentId = request.studentId,
        passwordHash = request.password
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
        request.role?.let { entity.role = it }
        return entity
    }

}