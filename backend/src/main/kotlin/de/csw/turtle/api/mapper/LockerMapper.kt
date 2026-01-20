package de.csw.turtle.api.mapper

import de.csw.turtle.api.dto.create.CreateLockerRequest
import de.csw.turtle.api.dto.get.GetLockerResponse
import de.csw.turtle.api.dto.patch.PatchLockerRequest
import de.csw.turtle.api.entity.LockerEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
abstract class LockerMapper() :
    CRUDMapper<LockerEntity, CreateLockerRequest, GetLockerResponse, PatchLockerRequest> {

    override fun create(request: CreateLockerRequest) = LockerEntity(
        index = request.index,
        name = request.name,
        isSoftwareUnlockable = request.isSoftwareUnlockable,
        locked = request.locked
    )

    override fun get(entity: LockerEntity) = GetLockerResponse(
        id = entity.id,
        name = entity.name,
        index = entity.index,
        isSoftwareUnlockable = entity.isSoftwareUnlockable,
        createdAt = entity.createdAt,
        locked = entity.locked
    )

    override fun patch(
        entity: LockerEntity,
        request: PatchLockerRequest
    ): LockerEntity {
        request.name?.let { entity.name = it }
        request.index?.let { entity.index = it }
        request.isSoftwareUnlockable?.let { entity.isSoftwareUnlockable = it }
        request.locked?.let { entity.locked = it }
        return entity
    }

}