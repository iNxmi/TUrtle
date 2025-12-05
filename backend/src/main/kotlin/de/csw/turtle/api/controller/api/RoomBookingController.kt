package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Permission
import de.csw.turtle.api.Permission.*
import de.csw.turtle.api.controller.CRUDCreateController
import de.csw.turtle.api.controller.CRUDDeleteController
import de.csw.turtle.api.controller.CRUDGetController
import de.csw.turtle.api.controller.CRUDPatchController
import de.csw.turtle.api.dto.create.CreateRoomBookingRequest
import de.csw.turtle.api.dto.get.GetRoomBookingResponse
import de.csw.turtle.api.dto.patch.PatchRoomBookingRequest
import de.csw.turtle.api.entity.RoomBookingEntity
import de.csw.turtle.api.mapper.RoomBookingMapper
import de.csw.turtle.api.service.RoomBookingService
import de.csw.turtle.api.service.SecurityService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/roombookings")
class RoomBookingController(
    override val endpoint: String = "/api/roombookings",

    override val createPermission: Permission = BACKEND__API_ROOMBOOKINGS__CREATE,
    override val getPermission: Permission = BACKEND__API_ROOMBOOKINGS__GET,
    override val patchPermission: Permission = BACKEND__API_ROOMBOOKINGS__PATCH,
    override val deletePermission: Permission = BACKEND__API_ROOMBOOKINGS__DELETE,

    override val service: RoomBookingService,
    override val mapper: RoomBookingMapper,
    override val securityService: SecurityService

) : CRUDCreateController<RoomBookingEntity, CreateRoomBookingRequest, GetRoomBookingResponse>,
    CRUDGetController<RoomBookingEntity, GetRoomBookingResponse>,
    CRUDPatchController<RoomBookingEntity, PatchRoomBookingRequest, GetRoomBookingResponse>,
    CRUDDeleteController<RoomBookingEntity>