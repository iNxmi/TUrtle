package de.csw.turtle.api.controller.api

import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.controller.CreateController
import de.csw.turtle.api.controller.DeleteController
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.controller.PatchController
import de.csw.turtle.api.dto.create.CreateTemplateRequest
import de.csw.turtle.api.dto.get.GetTemplateResponse
import de.csw.turtle.api.dto.patch.PatchTemplateRequest
import de.csw.turtle.api.entity.TemplateEntity
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
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
        user: UserEntity?,
        request: CreateTemplateRequest
    ): ResponseEntity<GetTemplateResponse> {
        TODO("Not yet implemented")
    }

    override fun get(
        user: UserEntity?,
        id: Long
    ): ResponseEntity<GetTemplateResponse> {
        TODO("Not yet implemented")
    }

    override fun getCollection(
        user: UserEntity?,
        rsql: String?,
        pageNumber: Int?,
        pageSize: Int,
        sortProperty: String?,
        sortDirection: Sort.Direction
    ): ResponseEntity<Any> {
        TODO("Not yet implemented")
    }

    override fun patch(
        user: UserEntity?,
        id: Long,
        request: PatchTemplateRequest
    ): ResponseEntity<GetTemplateResponse> {
        TODO("Not yet implemented")
    }

    override fun delete(
        user: UserEntity?,
        id: Long
    ): ResponseEntity<Void> {
        TODO("Not yet implemented")
    }

}