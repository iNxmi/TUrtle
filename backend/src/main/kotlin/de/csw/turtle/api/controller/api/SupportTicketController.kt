package de.csw.turtle.api.controller.api

import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.controller.CreateController
import de.csw.turtle.api.controller.DeleteController
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.controller.PatchController
import de.csw.turtle.api.dto.create.CreateSupportTicketRequest
import de.csw.turtle.api.dto.get.GetSupportTicketResponse
import de.csw.turtle.api.dto.patch.PatchSupportTicketRequest
import de.csw.turtle.api.entity.SupportTicketEntity
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/support-tickets")
class SupportTicketController
    : CreateController<SupportTicketEntity, CreateSupportTicketRequest, GetSupportTicketResponse>,
    GetController<SupportTicketEntity, GetSupportTicketResponse>,
    PatchController<SupportTicketEntity, PatchSupportTicketRequest, GetSupportTicketResponse>,
    DeleteController<SupportTicketEntity> {

    override fun create(
        user: UserEntity?,
        request: CreateSupportTicketRequest
    ): ResponseEntity<GetSupportTicketResponse> {
        TODO("Not yet implemented")
    }

    override fun get(
        user: UserEntity?,
        id: Long
    ): ResponseEntity<GetSupportTicketResponse> {
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
        request: PatchSupportTicketRequest
    ): ResponseEntity<GetSupportTicketResponse> {
        TODO("Not yet implemented")
    }

    override fun delete(
        user: UserEntity?,
        id: Long
    ): ResponseEntity<Void> {
        TODO("Not yet implemented")
    }

}