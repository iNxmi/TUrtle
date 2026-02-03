package de.csw.turtle.api.mapper

import de.csw.turtle.api.dto.create.CreateSupportTicketRequest
import de.csw.turtle.api.dto.get.GetSupportTicketResponse
import de.csw.turtle.api.dto.patch.PatchSupportTicketRequest
import de.csw.turtle.api.entity.SupportTicketEntity
import org.mapstruct.Mapper

@Mapper(componentModel = "spring")
abstract class SupportTicketMapper :
    CRUDMapper<SupportTicketEntity, CreateSupportTicketRequest, GetSupportTicketResponse, PatchSupportTicketRequest> {

    override fun create(request: CreateSupportTicketRequest) = SupportTicketEntity(
        urgency = request.urgency,
        category = request.category,
        email = request.email,
        subject = request.subject,
        description = request.description
    )

    override fun get(entity: SupportTicketEntity) = GetSupportTicketResponse(
        id = entity.id,
        urgency = entity.urgency,
        category = entity.category,
        email = entity.email,
        subject = entity.subject,
        description = entity.description,
        status = entity.status,
        updatedAt = entity.updatedAt,
        createdAt = entity.createdAt
    )

    override fun patch(
        entity: SupportTicketEntity,
        request: PatchSupportTicketRequest
    ): SupportTicketEntity {
        request.urgency?.let { entity.urgency = it }
        request.category?.let { entity.category = it }
        request.email?.let { entity.email = it }
        request.subject?.let { entity.subject = it }
        request.description?.let { entity.description = it }
        request.status?.let { entity.status = it }
        return entity
    }

}