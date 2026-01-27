package de.csw.turtle.api.controller.api

import de.csw.turtle.api.controller.CreateController
import de.csw.turtle.api.controller.DeleteController
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.controller.PatchController
import de.csw.turtle.api.dto.create.CreateSupportTicketRequest
import de.csw.turtle.api.dto.get.GetSupportTicketResponse
import de.csw.turtle.api.dto.patch.PatchSupportTicketRequest
import de.csw.turtle.api.entity.SupportTicketEntity
import de.csw.turtle.api.mapper.SupportTicketMapper
import de.csw.turtle.api.service.SupportTicketService
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/support-tickets")
class SupportTicketController(
    override val endpoint: String = "/api/support-tickets",
    override val service: SupportTicketService,
    override val mapper: SupportTicketMapper
) : CreateController<SupportTicketEntity, CreateSupportTicketRequest, GetSupportTicketResponse>,
    GetController<SupportTicketEntity, GetSupportTicketResponse>,
    PatchController<SupportTicketEntity, PatchSupportTicketRequest, GetSupportTicketResponse>,
    DeleteController<SupportTicketEntity> {

    @PreAuthorize("hasAuthority('MANAGE_SUPPORT_TICKETS')")
    override fun get(id: Long) = super.get(id)

    @PreAuthorize("hasAuthority('MANAGE_SUPPORT_TICKETS')")
    override fun getCollection(
        rsql: String?,
        pageNumber: Int?,
        pageSize: Int,
        sortProperty: String?,
        sortDirection: Sort.Direction
    ): ResponseEntity<Any> = super.getCollection(rsql, pageNumber, pageSize, sortProperty, sortDirection)

    @PreAuthorize("hasAuthority('MANAGE_SUPPORT_TICKETS')")
    override fun patch(id: Long, request: PatchSupportTicketRequest) = super.patch(id, request)

    @PreAuthorize("hasAuthority('MANAGE_SUPPORT_TICKETS')")
    override fun delete(id: Long) = super.delete(id)

}