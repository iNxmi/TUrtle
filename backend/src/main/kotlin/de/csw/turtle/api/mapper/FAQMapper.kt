package de.csw.turtle.api.mapper

import de.csw.turtle.api.dto.create.CreateFAQRequest
import de.csw.turtle.api.dto.get.GetFAQResponse
import de.csw.turtle.api.dto.patch.PatchFAQRequest
import de.csw.turtle.api.entity.FAQEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
abstract class FAQMapper : CRUDMapper<FAQEntity, CreateFAQRequest, GetFAQResponse, PatchFAQRequest> {

    override fun create(request: CreateFAQRequest) = FAQEntity(
        name = request.name,
        title = request.title,
        markdown = request.content
    )

    override fun get(entity: FAQEntity) = GetFAQResponse(
        id = entity.id,
        name = entity.name,
        title = entity.title,
        content = entity.markdown,
        createdAt = entity.createdAt
    )

    override fun patch(
        entity: FAQEntity,
        request: PatchFAQRequest
    ): FAQEntity {
        request.name?.let { entity.name = it }
        request.title?.let { entity.title = it }
        request.content?.let { entity.markdown = it }
        return entity
    }

}