package de.csw.turtle.api.controller.api.manage

import de.csw.turtle.api.Permission
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
@RequestMapping("/api/manage/room-bookings")
class ManageRoomBookingController(
    override val endpoint: String = "/api/manage/room-bookings",

    override val permissionCreate: Permission = Permission.BACKEND__API_ROOMBOOKINGS__CREATE,
    override val permissionGet: Permission = Permission.BACKEND__API_ROOMBOOKINGS__GET,
    override val permissionPatch: Permission = Permission.BACKEND__API_ROOMBOOKINGS__PATCH,
    override val permissionDelete: Permission = Permission.BACKEND__API_ROOMBOOKINGS__DELETE,

    override val service: RoomBookingService,
    override val mapper: RoomBookingMapper,
    override val permissionService: PermissionService
) : CreateController<RoomBookingEntity, CreateRoomBookingRequest, GetRoomBookingResponse>,
    GetController<RoomBookingEntity, GetRoomBookingResponse>,
    PatchController<RoomBookingEntity, PatchRoomBookingRequest, GetRoomBookingResponse>,
    DeleteController<RoomBookingEntity>