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
        markdown = request.markdown
    )

    override fun get(entity: TemplateEntity) = GetTemplateResponse(
        id = entity.id,
        name = entity.name,
        description = entity.description,
        markdown = entity.markdown,
        createdAt = entity.createdAt
    )

    override fun patch(
        entity: TemplateEntity,
        request: PatchTemplateRequest
    ): TemplateEntity {
        request.name?.let { entity.name = it }
        request.description?.let { entity.description = it }
        request.markdown?.let { entity.markdown = it }
        return entity
    }

}