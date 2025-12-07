package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Permission
import de.csw.turtle.api.Permission.*
import de.csw.turtle.api.controller.CRUDCreateController
import de.csw.turtle.api.controller.CRUDDeleteController
import de.csw.turtle.api.controller.CRUDGetController
import de.csw.turtle.api.controller.CRUDPatchController
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
@RequestMapping("/api/support")
class SupportTicketController(
    override val endpoint: String = "/api/support",

    override val permissionCreate: Permission = BACKEND__API_SUPPORTTICKETS__CREATE,
    override val permissionGet: Permission = BACKEND__API_SUPPORTTICKETS__GET,
    override val permissionPatch: Permission = BACKEND__API_SUPPORTTICKETS__PATCH,
    override val permissionDelete: Permission = BACKEND__API_SUPPORTTICKETS__DELETE,

    override val service: SupportTicketService,
    override val mapper: SupportTicketMapper,
    override val permissionService: PermissionService
) : CRUDCreateController<SupportTicketEntity, CreateSupportTicketRequest, GetSupportTicketResponse>,
    CRUDGetController<SupportTicketEntity, GetSupportTicketResponse>,
    CRUDPatchController<SupportTicketEntity, PatchSupportTicketRequest, GetSupportTicketResponse>,
    CRUDDeleteController<SupportTicketEntity>