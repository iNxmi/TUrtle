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
        name = request.name
    )

    override fun get(entity: LockerEntity) = GetLockerResponse(
        id = entity.id,
        index = entity.index,
        name = entity.name,
        createdAt = entity.createdAt
    )

    override fun patch(
        entity: LockerEntity,
        request: PatchLockerRequest
    ): LockerEntity {
        request.index?.let { entity.index = it }
        request.name?.let { entity.name = it }
        return entity
    }

}