package de.csw.turtle.api.mapper

import de.csw.turtle.api.dto.create.CreateTemplateRequest
import de.csw.turtle.api.dto.get.GetTemplateResponse
import de.csw.turtle.api.dto.patch.PatchTemplateRequest
import de.csw.turtle.api.entity.TemplateEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
abstract class TemplateMapper : CRUDMapper<TemplateEntity, CreateTemplateRequest, GetTemplateResponse, PatchTemplateRequest> {

    override fun create(request: CreateTemplateRequest) = TemplateEntity(
        name = request.name,
        description = request.description,
        content = request.content
    )

    override fun get(entity: TemplateEntity) = GetTemplateResponse(
        id = entity.id,
        name = entity.name,
        description = entity.description,
        content = entity.content,
        updatedAt = entity.updatedAt,
        createdAt = entity.createdAt
    )

    override fun patch(
        entity: TemplateEntity,
        request: PatchTemplateRequest
    ): TemplateEntity {
        request.name?.let { entity.name = it }
        request.description?.let { entity.description = it }
        request.content?.let { entity.content = it }
        return entity
    }

}