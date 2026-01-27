package de.csw.turtle.api.controller.api

import de.csw.turtle.api.controller.CreateController
import de.csw.turtle.api.controller.DeleteController
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.controller.PatchController
import de.csw.turtle.api.dto.create.CreateRoomBookingRequest
import de.csw.turtle.api.dto.create.CreateSupportTicketRequest
import de.csw.turtle.api.dto.get.GetRoomBookingResponse
import de.csw.turtle.api.dto.patch.PatchRoomBookingRequest
import de.csw.turtle.api.dto.patch.PatchSupportTicketRequest
import de.csw.turtle.api.entity.RoomBookingEntity
import de.csw.turtle.api.mapper.RoomBookingMapper
import de.csw.turtle.api.service.RoomBookingService
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/room-bookings")
class RoomBookingController(
    override val endpoint: String = "/api/room-bookings",
    override val service: RoomBookingService,
    override val mapper: RoomBookingMapper
) : CreateController<RoomBookingEntity, CreateRoomBookingRequest, GetRoomBookingResponse>,
    GetController<RoomBookingEntity, GetRoomBookingResponse>,
    PatchController<RoomBookingEntity, PatchRoomBookingRequest, GetRoomBookingResponse>,
    DeleteController<RoomBookingEntity> {

    @PreAuthorize("hasAuthority('MANAGE_ROOM_BOOKINGS')")
    override fun create(request: CreateRoomBookingRequest) = super.create(request)

    @PreAuthorize("hasAuthority('MANAGE_ROOM_BOOKINGS')")
    override fun get(id: Long) = super.get(id)

    @PreAuthorize("hasAuthority('MANAGE_ROOM_BOOKINGS')")
    override fun getCollection(
        rsql: String?,
        pageNumber: Int?,
        pageSize: Int,
        sortProperty: String?,
        sortDirection: Sort.Direction
    ): ResponseEntity<Any> = super.getCollection(rsql, pageNumber, pageSize, sortProperty, sortDirection)

    @PreAuthorize("hasAuthority('MANAGE_ROOM_BOOKINGS')")
    override fun patch(id: Long, request: PatchRoomBookingRequest) = super.patch(id, request)

    @PreAuthorize("hasAuthority('MANAGE_ROOM_BOOKINGS')")
    override fun delete(id: Long) = super.delete(id)

}