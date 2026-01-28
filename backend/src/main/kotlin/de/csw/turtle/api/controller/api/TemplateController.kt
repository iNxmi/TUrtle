package de.csw.turtle.api.controller.api

import de.csw.turtle.api.SimpleUserDetails
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
class TemplateController :
    CreateController<TemplateEntity, CreateTemplateRequest, GetTemplateResponse>,
    GetController<TemplateEntity, GetTemplateResponse>,
    PatchController<TemplateEntity, PatchTemplateRequest, GetTemplateResponse>,
    DeleteController<TemplateEntity> {

    override fun create(
        userDetails: SimpleUserDetails?,
        request: CreateTemplateRequest
    ): ResponseEntity<GetTemplateResponse> {
        TODO("Not yet implemented")
    }

    override fun get(
        userDetails: SimpleUserDetails?,
        id: Long
    ): ResponseEntity<GetTemplateResponse> {
        TODO("Not yet implemented")
    }

    override fun getCollection(
        userDetails: SimpleUserDetails?,
        rsql: String?,
        pageNumber: Int?,
        pageSize: Int,
        sortProperty: String?,
        sortDirection: Sort.Direction
    ): ResponseEntity<Any> {
        TODO("Not yet implemented")
    }

    override fun patch(
        userDetails: SimpleUserDetails?,
        id: Long,
        request: PatchTemplateRequest
    ): ResponseEntity<GetTemplateResponse> {
        TODO("Not yet implemented")
    }

    override fun delete(
        userDetails: SimpleUserDetails?,
        id: Long
    ): ResponseEntity<Void> {
        TODO("Not yet implemented")
    }

}