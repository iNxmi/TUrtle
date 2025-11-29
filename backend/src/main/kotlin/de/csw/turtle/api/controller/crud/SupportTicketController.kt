package de.csw.turtle.api.controller.crud

import de.csw.turtle.api.dto.create.CreateSupportTicketRequest
import de.csw.turtle.api.dto.get.GetSupportTicketResponse
import de.csw.turtle.api.dto.patch.PatchSupportTicketRequest
import de.csw.turtle.api.entity.SupportTicketEntity
import de.csw.turtle.api.mapper.SupportTicketMapper
import de.csw.turtle.api.repository.SupportTicketRepository
import de.csw.turtle.api.service.SupportTicketService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/support")
class SupportTicketController(
    override val service: SupportTicketService,
    override val mapper: SupportTicketMapper
) : CRUDController<SupportTicketEntity, CreateSupportTicketRequest, GetSupportTicketResponse, PatchSupportTicketRequest, SupportTicketRepository, SupportTicketMapper, SupportTicketService>(
    "/api/support",
    "api.support"
)