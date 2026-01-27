package de.csw.turtle.api.controller.api

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
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/faq")
class FAQController(
    override val endpoint: String = "/api/faq",
    override val service: FAQService,
    override val mapper: FAQMapper,
) : CreateController<FAQEntity, CreateFAQRequest, GetFAQResponse>,
    GetController<FAQEntity, GetFAQResponse>,
    PatchController<FAQEntity, PatchFAQRequest, GetFAQResponse>,
    DeleteController<FAQEntity> {

    @PreAuthorize("hasAuthority('MANAGE_FAQ')")
    override fun create(request: CreateFAQRequest) = super.create(request)

    @PreAuthorize("hasAuthority('MANAGE_FAQ')")
    override fun patch(id: Long, request: PatchFAQRequest) = super.patch(id, request)

    @PreAuthorize("hasAuthority('MANAGE_FAQ')")
    override fun delete(id: Long) = super.delete(id)

}