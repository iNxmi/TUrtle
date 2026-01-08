package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Permission
import de.csw.turtle.api.Permission.*
import de.csw.turtle.api.controller.CreateController
import de.csw.turtle.api.controller.DeleteController
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.controller.PatchController
import de.csw.turtle.api.dto.create.CreateRoomBookingRequest
import de.csw.turtle.api.dto.get.GetRoomBookingResponse
import de.csw.turtle.api.dto.patch.PatchRoomBookingRequest
import de.csw.turtle.api.entity.RoomBookingEntity
import de.csw.turtle.api.mapper.RoomBookingMapper
import de.csw.turtle.api.service.PermissionService
import de.csw.turtle.api.service.RoomBookingService
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/roombookings")
class RoomBookingController(
    override val endpoint: String = "/api/roombookings",

    override val permissionCreate: Permission = BACKEND__API_ROOMBOOKINGS__CREATE,
    override val permissionGet: Permission = BACKEND__API_ROOMBOOKINGS__GET,
    override val permissionPatch: Permission = BACKEND__API_ROOMBOOKINGS__PATCH,
    override val permissionDelete: Permission = BACKEND__API_ROOMBOOKINGS__DELETE,

    override val service: RoomBookingService,
    override val mapper: RoomBookingMapper,
    override val permissionService: PermissionService
) : CreateController<RoomBookingEntity, CreateRoomBookingRequest, GetRoomBookingResponse>,
    GetController<RoomBookingEntity, GetRoomBookingResponse>,
    PatchController<RoomBookingEntity, PatchRoomBookingRequest, GetRoomBookingResponse>,
    DeleteController<RoomBookingEntity>