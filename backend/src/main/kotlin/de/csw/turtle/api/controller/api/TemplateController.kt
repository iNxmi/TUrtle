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
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
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
    DeleteController<TemplateEntity> {

    @PreAuthorize("hasAuthority('MANAGE_TEMPLATES')")
    override fun create(request: CreateTemplateRequest) = super.create(request)

    @PreAuthorize("hasAuthority('MANAGE_TEMPLATES')")
    override fun get(id: Long) = super.get(id)

    @PreAuthorize("hasAuthority('MANAGE_TEMPLATES')")
    override fun getCollection(
        rsql: String?,
        pageNumber: Int?,
        pageSize: Int,
        sortProperty: String?,
        sortDirection: Sort.Direction
    ): ResponseEntity<Any> = super.getCollection(rsql, pageNumber, pageSize, sortProperty, sortDirection)

    @PreAuthorize("hasAuthority('MANAGE_TEMPLATES')")
    override fun patch(id: Long, request: PatchTemplateRequest) = super.patch(id, request)

    @PreAuthorize("hasAuthority('MANAGE_TEMPLATES')")
    override fun delete(id: Long) = super.delete(id)

}