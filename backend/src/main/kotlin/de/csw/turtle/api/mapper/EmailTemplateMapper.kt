package de.csw.turtle.api.mapper

import de.csw.turtle.api.dto.create.CreateEmailTemplateRequest
import de.csw.turtle.api.dto.get.GetEmailTemplateResponse
import de.csw.turtle.api.dto.patch.PatchEmailTemplateRequest
import de.csw.turtle.api.entity.EmailTemplateEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
abstract class EmailTemplateMapper :
    CRUDMapper<EmailTemplateEntity, CreateEmailTemplateRequest, GetEmailTemplateResponse, PatchEmailTemplateRequest> {

    override fun create(request: CreateEmailTemplateRequest) = EmailTemplateEntity(
        name = request.name,
        description = request.description,
        subject = request.subject,
        content = request.content
    )

    override fun get(entity: EmailTemplateEntity) = GetEmailTemplateResponse(
        id = entity.id,
        name = entity.name,
        description = entity.description,
        subject = entity.subject,
        content = entity.content,
        updatedAt = entity.updatedAt,
        createdAt = entity.createdAt
    )

    override fun patch(
        entity: EmailTemplateEntity,
        request: PatchEmailTemplateRequest
    ): EmailTemplateEntity {
        request.name?.let { entity.name = it }
        request.description?.let { entity.description = it }
        request.subject?.let { entity.subject = it }
        request.content?.let { entity.content = it }
        return entity
    }

}