package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Permission
import de.csw.turtle.api.Permission.*
import de.csw.turtle.api.controller.CRUDCreateController
import de.csw.turtle.api.controller.CRUDDeleteController
import de.csw.turtle.api.controller.CRUDGetController
import de.csw.turtle.api.controller.CRUDPatchController
import de.csw.turtle.api.dto.create.CreateFAQRequest
import de.csw.turtle.api.dto.get.GetFAQResponse
import de.csw.turtle.api.dto.patch.PatchFAQRequest
import de.csw.turtle.api.entity.FAQEntity
import de.csw.turtle.api.mapper.FAQMapper
import de.csw.turtle.api.service.FAQService
import de.csw.turtle.api.service.SecurityService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/faq")
class FAQController(
    override val endpoint: String = "/api/faq",

    override val createPermission: Permission = BACKEND__API_FAQ__CREATE,
    override val getPermission: Permission = BACKEND__API_FAQ__GET,
    override val patchPermission: Permission = BACKEND__API_FAQ__PATCH,
    override val deletePermission: Permission = BACKEND__API_FAQ__DELETE,

    override val service: FAQService,
    override val mapper: FAQMapper,
    override val securityService: SecurityService
) : CRUDCreateController<FAQEntity, CreateFAQRequest, GetFAQResponse>,
    CRUDGetController<FAQEntity, GetFAQResponse>,
    CRUDPatchController<FAQEntity, PatchFAQRequest, GetFAQResponse>,
    CRUDDeleteController<FAQEntity>