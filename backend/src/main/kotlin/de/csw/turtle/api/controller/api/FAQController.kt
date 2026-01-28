package de.csw.turtle.api.controller.api

import de.csw.turtle.api.SimpleUserDetails
import de.csw.turtle.api.controller.CreateController
import de.csw.turtle.api.controller.DeleteController
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.controller.PatchController
import de.csw.turtle.api.dto.create.CreateFAQRequest
import de.csw.turtle.api.dto.get.GetFAQResponse
import de.csw.turtle.api.dto.patch.PatchFAQRequest
import de.csw.turtle.api.entity.FAQEntity
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/faq")
class FAQController :
    CreateController<FAQEntity, CreateFAQRequest, GetFAQResponse>,
    GetController<FAQEntity, GetFAQResponse>,
    PatchController<FAQEntity, PatchFAQRequest, GetFAQResponse>,
    DeleteController<FAQEntity> {

    override fun create(
        userDetails: SimpleUserDetails?,
        request: CreateFAQRequest
    ): ResponseEntity<GetFAQResponse> {
        TODO("Not yet implemented")
    }

    override fun get(
        userDetails: SimpleUserDetails?,
        id: Long
    ): ResponseEntity<GetFAQResponse> {
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
        request: PatchFAQRequest
    ): ResponseEntity<GetFAQResponse> {
        TODO("Not yet implemented")
    }

    override fun delete(
        userDetails: SimpleUserDetails?,
        id: Long
    ): ResponseEntity<Void> {
        TODO("Not yet implemented")
    }

}