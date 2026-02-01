package de.csw.turtle.api.mapper

import de.csw.turtle.api.dto.create.CreateGeneralTemplateRequest
import de.csw.turtle.api.dto.get.GetGeneralTemplateResponse
import de.csw.turtle.api.dto.patch.PatchGeneralTemplateRequest
import de.csw.turtle.api.entity.GeneralTemplateEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
abstract class GeneralTemplateMapper : CRUDMapper<GeneralTemplateEntity, CreateGeneralTemplateRequest, GetGeneralTemplateResponse, PatchGeneralTemplateRequest> {

    override fun create(request: CreateGeneralTemplateRequest) = GeneralTemplateEntity(
        name = request.name,
        description = request.description,
        content = request.content
    )

    override fun get(entity: GeneralTemplateEntity) = GetGeneralTemplateResponse(
        id = entity.id,
        name = entity.name,
        description = entity.description,
        content = entity.content,
        updatedAt = entity.updatedAt,
        createdAt = entity.createdAt
    )

    override fun patch(
        entity: GeneralTemplateEntity,
        request: PatchGeneralTemplateRequest
    ): GeneralTemplateEntity {
        request.name?.let { entity.name = it }
        request.description?.let { entity.description = it }
        request.content?.let { entity.content = it }
        return entity
    }

}