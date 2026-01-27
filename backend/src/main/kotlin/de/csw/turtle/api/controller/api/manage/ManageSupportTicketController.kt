package de.csw.turtle.api.controller.api.manage

import de.csw.turtle.api.Permission
import de.csw.turtle.api.Permission.*
import de.csw.turtle.api.controller.CreateController
import de.csw.turtle.api.controller.DeleteController
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.controller.PatchController
import de.csw.turtle.api.dto.create.CreateSupportTicketRequest
import de.csw.turtle.api.dto.get.GetSupportTicketResponse
import de.csw.turtle.api.dto.patch.PatchSupportTicketRequest
import de.csw.turtle.api.entity.SupportTicketEntity
import de.csw.turtle.api.mapper.SupportTicketMapper
import de.csw.turtle.api.service.PermissionService
import de.csw.turtle.api.service.SupportTicketService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/manage/support-tickets")
class ManageSupportTicketController(
    override val endpoint: String = "/api/manage/support-tickets",

    override val permissionCreate: Permission? = null,
    override val permissionGet: Permission = BACKEND__API_SUPPORTTICKETS__GET,
    override val permissionPatch: Permission = BACKEND__API_SUPPORTTICKETS__PATCH,
    override val permissionDelete: Permission = BACKEND__API_SUPPORTTICKETS__DELETE,

    override val service: SupportTicketService,
    override val mapper: SupportTicketMapper,
    override val permissionService: PermissionService
) : CreateController<SupportTicketEntity, CreateSupportTicketRequest, GetSupportTicketResponse>,
    GetController<SupportTicketEntity, GetSupportTicketResponse>,
    PatchController<SupportTicketEntity, PatchSupportTicketRequest, GetSupportTicketResponse>,
    DeleteController<SupportTicketEntity>