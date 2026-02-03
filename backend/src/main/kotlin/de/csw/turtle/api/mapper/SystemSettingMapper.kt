package de.csw.turtle.api.mapper

import de.csw.turtle.api.dto.create.CreateSystemSettingRequest
import de.csw.turtle.api.dto.get.GetSystemSettingResponse
import de.csw.turtle.api.dto.patch.PatchSystemSettingRequest
import de.csw.turtle.api.entity.SystemSettingEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
abstract class SystemSettingMapper : CRUDMapper<SystemSettingEntity, CreateSystemSettingRequest, GetSystemSettingResponse, PatchSystemSettingRequest> {

    override fun create(request: CreateSystemSettingRequest) = SystemSettingEntity(
        key = request.key,
        type = request.type,
        value = request.value
    )

    override fun get(entity: SystemSettingEntity) = GetSystemSettingResponse(
        id = entity.id,
        key = entity.key,
        type = entity.type,
        value = entity.value,
        updatedAt = entity.updatedAt,
        createdAt = entity.createdAt
    )

    override fun patch(
        entity: SystemSettingEntity,
        request: PatchSystemSettingRequest
    ): SystemSettingEntity {
        request.key?.let { entity.key = it }
        request.type?.let { entity.type = it }
        request.value?.let { entity.value = it }
        return entity
    }

}