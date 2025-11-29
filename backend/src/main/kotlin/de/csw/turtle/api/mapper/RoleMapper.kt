package de.csw.turtle.api.mapper

import de.csw.turtle.api.dto.create.CreateRoleRequest
import de.csw.turtle.api.dto.get.GetRoleResponse
import de.csw.turtle.api.dto.patch.PatchRoleRequest
import de.csw.turtle.api.entity.RoleEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
abstract class RoleMapper : CRUDMapper<RoleEntity, CreateRoleRequest, GetRoleResponse, PatchRoleRequest> {

    override fun create(request: CreateRoleRequest) = RoleEntity(
        name = request.name,
        permissions = request.permissions.toMutableSet()
    )

    override fun get(entity: RoleEntity) = GetRoleResponse(
        id = entity.id,
        name = entity.name,
        permissions = entity.permissions,
        createdAt = entity.createdAt
    )

    override fun patch(
        entity: RoleEntity,
        request: PatchRoleRequest
    ): RoleEntity {
        request.name?.let { entity.name = it }

        if (request.permissions != null) {
            entity.permissions.clear()
            entity.permissions.addAll(request.permissions)
        }

        return entity
    }

}