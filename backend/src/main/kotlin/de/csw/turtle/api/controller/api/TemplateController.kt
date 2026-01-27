package de.csw.turtle.api.controller.api

import de.csw.turtle.api.controller.CreateController
import de.csw.turtle.api.controller.DeleteController
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.controller.PatchController
import de.csw.turtle.api.dto.create.CreateTemplateRequest
import de.csw.turtle.api.dto.get.GetTemplateResponse
import de.csw.turtle.api.dto.patch.PatchTemplateRequest
import de.csw.turtle.api.entity.TemplateEntity
import de.csw.turtle.api.mapper.TemplateMapper
import de.csw.turtle.api.service.TemplateService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/templates")
class TemplateController(
    override val endpoint: String = "/api/templates",
    override val service: TemplateService,
    override val mapper: TemplateMapper
) : CreateController<TemplateEntity, CreateTemplateRequest, GetTemplateResponse>,
    GetController<TemplateEntity, GetTemplateResponse>,
    PatchController<TemplateEntity, PatchTemplateRequest, GetTemplateResponse>,
    DeleteController<TemplateEntity>