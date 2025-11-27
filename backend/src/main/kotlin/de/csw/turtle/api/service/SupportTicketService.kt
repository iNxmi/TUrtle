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
) : CRUDService<SupportTicketEntity, CreateSupportTicketRequest, GetSupportTicketResponse, PatchSupportTicketRequest, SupportTicketRepository, SupportTicketMapper>()