package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Permission
import de.csw.turtle.api.Permission.*
import de.csw.turtle.api.controller.CRUDCreateController
import de.csw.turtle.api.controller.CRUDDeleteController
import de.csw.turtle.api.controller.CRUDGetController
import de.csw.turtle.api.controller.CRUDPatchController
import de.csw.turtle.api.dto.create.CreateTemplateRequest
import de.csw.turtle.api.dto.get.GetTemplateResponse
import de.csw.turtle.api.dto.patch.PatchTemplateRequest
import de.csw.turtle.api.entity.TemplateEntity
import de.csw.turtle.api.mapper.TemplateMapper
import de.csw.turtle.api.service.PermissionService
import de.csw.turtle.api.service.TemplateService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/templates")
class TemplateController(
    override val endpoint: String = "/api/templates",

    override val permissionCreate: Permission = BACKEND__API_TEMPLATES__CREATE,
    override val permissionGet: Permission = BACKEND__API_TEMPLATES__GET,
    override val permissionPatch: Permission = BACKEND__API_TEMPLATES__PATCH,
    override val permissionDelete: Permission = BACKEND__API_TEMPLATES__DELETE,

    override val service: TemplateService,
    override val mapper: TemplateMapper,
    override val permissionService: PermissionService
) : CRUDCreateController<TemplateEntity, CreateTemplateRequest, GetTemplateResponse>,
    CRUDGetController<TemplateEntity, GetTemplateResponse>,
    CRUDPatchController<TemplateEntity, PatchTemplateRequest, GetTemplateResponse>,
    CRUDDeleteController<TemplateEntity>
