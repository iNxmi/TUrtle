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
import de.csw.turtle.api.service.RoomBookingService
import de.csw.turtle.api.service.PermissionService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

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
    override val permissionService: PermissionService,

    private val roomBookingService: RoomBookingService,
    private val roomBookingMapper: RoomBookingMapper

) : CreateController<RoomBookingEntity, CreateRoomBookingRequest, GetRoomBookingResponse>,
    GetController<RoomBookingEntity, GetRoomBookingResponse>,
    PatchController<RoomBookingEntity, PatchRoomBookingRequest, GetRoomBookingResponse>,
    DeleteController<RoomBookingEntity> {

    @GetMapping("/overlapping")
    fun getOverlapping(
        @RequestParam start: Instant,
        @RequestParam end: Instant
    ): ResponseEntity<Set<GetRoomBookingResponse>> {
        permissionService.check(permissionGet)

        val all = roomBookingService.getAllOverlapping(start, end)
        val mapped = all.map { roomBookingMapper.get(it) }.toSet()
        return ResponseEntity.ok(mapped)
    }

}