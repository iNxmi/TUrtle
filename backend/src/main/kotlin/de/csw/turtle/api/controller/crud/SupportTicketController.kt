package de.csw.turtle.api.controller.crud

import de.csw.turtle.api.Permission
import de.csw.turtle.api.dto.create.CreateSupportTicketRequest
import de.csw.turtle.api.dto.get.GetSupportTicketResponse
import de.csw.turtle.api.dto.patch.PatchSupportTicketRequest
import de.csw.turtle.api.entity.SupportTicketEntity
import de.csw.turtle.api.mapper.SupportTicketMapper
import de.csw.turtle.api.service.SecurityService
import de.csw.turtle.api.service.SupportTicketService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/support")
class SupportTicketController(
    override val endpoint: String = "/api/support",

    override val createPermission: Permission = Permission.BACKEND__API_SUPPORTTICKETS__CREATE,
    override val getPermission: Permission = Permission.BACKEND__API_SUPPORTTICKETS__GET,
    override val patchPermission: Permission = Permission.BACKEND__API_SUPPORTTICKETS__PATCH,
    override val deletePermission: Permission = Permission.BACKEND__API_SUPPORTTICKETS__DELETE,

    override val service: SupportTicketService,
    override val mapper: SupportTicketMapper,
    override val securityService: SecurityService
) : CreateController<SupportTicketEntity, CreateSupportTicketRequest, GetSupportTicketResponse>,
    GetController<SupportTicketEntity, GetSupportTicketResponse>,
    PatchController<SupportTicketEntity, PatchSupportTicketRequest, GetSupportTicketResponse>,
    DeleteController<SupportTicketEntity>