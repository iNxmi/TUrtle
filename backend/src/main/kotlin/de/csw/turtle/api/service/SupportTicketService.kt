package de.csw.turtle.api.service

import de.csw.turtle.api.dto.create.CreateSupportTicketRequest
import de.csw.turtle.api.dto.get.GetSupportTicketResponse
import de.csw.turtle.api.dto.patch.PatchSupportTicketRequest
import de.csw.turtle.api.entity.SupportTicketEntity
import de.csw.turtle.api.mapper.SupportTicketMapper
import de.csw.turtle.api.repository.SupportTicketRepository
import org.springframework.stereotype.Service

@Service
class SupportTicketService(
    override val repository: SupportTicketRepository,
    override val mapper: SupportTicketMapper
) : CRUDService<SupportTicketEntity, CreateSupportTicketRequest, GetSupportTicketResponse, PatchSupportTicketRequest>("SupportTicket") {

    private val regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$".toRegex()

    override fun create(request: CreateSupportTicketRequest): SupportTicketEntity {
        if(!SupportTicketEntity.Urgency.entries.contains(request.urgency))
            throw IllegalArgumentException("'${request.urgency}' is not a valid Urgency")
        if(!SupportTicketEntity.Category.entries.contains(request.category))
            throw IllegalArgumentException("'${request.category}' is not a valid Category")
        if(!regex.matches(request.email))
            throw IllegalArgumentException("'${request.email}' is not a valid Email")
        return super.create(request)
    }

    override fun patch(id: Long, request: PatchSupportTicketRequest): SupportTicketEntity {
        return super.patch(id, request)
    }
}