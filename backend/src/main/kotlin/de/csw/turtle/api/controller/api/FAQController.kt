package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Permission
import de.csw.turtle.api.Permission.*
import de.csw.turtle.api.controller.CreateController
import de.csw.turtle.api.controller.DeleteController
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.controller.PatchController
import de.csw.turtle.api.dto.create.CreateFAQRequest
import de.csw.turtle.api.dto.get.GetFAQResponse
import de.csw.turtle.api.dto.patch.PatchFAQRequest
import de.csw.turtle.api.entity.FAQEntity
import de.csw.turtle.api.mapper.FAQMapper
import de.csw.turtle.api.service.FAQService
import de.csw.turtle.api.service.PermissionService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/faq")
class FAQController(
    override val endpoint: String = "/api/faq",

    override val permissionCreate: Permission = BACKEND__API_FAQ__CREATE,
    override val permissionGet: Permission? = null,
    override val permissionPatch: Permission = BACKEND__API_FAQ__PATCH,
    override val permissionDelete: Permission = BACKEND__API_FAQ__DELETE,

    override val service: FAQService,
    override val mapper: FAQMapper,
    override val permissionService: PermissionService
) : CreateController<FAQEntity, CreateFAQRequest, GetFAQResponse>,
    GetController<FAQEntity, GetFAQResponse>,
    PatchController<FAQEntity, PatchFAQRequest, GetFAQResponse>,
    DeleteController<FAQEntity>