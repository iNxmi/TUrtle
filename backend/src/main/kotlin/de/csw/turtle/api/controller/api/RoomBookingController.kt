package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Permission
import de.csw.turtle.api.controller.CreateController
import de.csw.turtle.api.controller.DeleteController
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.controller.PatchController
import de.csw.turtle.api.dto.create.CreateRoomBookingRequest
import de.csw.turtle.api.dto.get.GetRoomBookingResponse
import de.csw.turtle.api.dto.patch.PatchRoomBookingRequest
import de.csw.turtle.api.entity.DeviceBookingEntity
import de.csw.turtle.api.entity.RoomBookingEntity
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.ForbiddenException
import de.csw.turtle.api.exception.UnauthorizedException
import de.csw.turtle.api.mapper.RoomBookingMapper
import de.csw.turtle.api.service.RoomBookingService
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/api/room-bookings")
class RoomBookingController(
    private val roomBookingService: RoomBookingService,
    private val roomBookingMapper: RoomBookingMapper
) : CreateController<RoomBookingEntity, CreateRoomBookingRequest, GetRoomBookingResponse>,
    GetController<RoomBookingEntity, GetRoomBookingResponse>,
    PatchController<RoomBookingEntity, PatchRoomBookingRequest, GetRoomBookingResponse>,
    DeleteController<RoomBookingEntity> {

    override fun create(
        user: UserEntity?,
        request: CreateRoomBookingRequest
    ): ResponseEntity<GetRoomBookingResponse> {
        if (user == null)
            throw UnauthorizedException()

        val sanitized = if (!user.hasPermission(Permission.MANAGE_ROOM_BOOKINGS)) {
            request.copy(userId = user.id)
        } else request

        val entity = roomBookingService.create(sanitized)
        val location = URI.create("/api/room-bookings/${entity.id}")
        val dto = roomBookingMapper.get(entity)
        return ResponseEntity.created(location).body(dto)
    }

    override fun get(
        user: UserEntity?,
        id: Long
    ): ResponseEntity<GetRoomBookingResponse> {
        val entity = roomBookingService.get(id)
        val dto = roomBookingMapper.get(entity)
        return ResponseEntity.ok(dto)
    }

    override fun getCollection(
        user: UserEntity?,
        rsql: String?,
        pageNumber: Int?,
        pageSize: Int,
        sortProperty: String?,
        sortDirection: Sort.Direction
    ): ResponseEntity<Any> {
        if (user == null)
            throw UnauthorizedException()

        val sort = sortProperty?.let {
            Sort.by(sortDirection, sortProperty)
        } ?: Sort.unsorted()

        if (pageNumber != null) {
            val pageable = PageRequest.of(pageNumber, pageSize, sort)
            val page = roomBookingService.getPage(rsql = rsql, pageable = pageable)
            val dto = page.map { roomBookingMapper.get(it) }
            return ResponseEntity.ok(dto)
        }

        val collection = roomBookingService.getAll(rsql = rsql, sort = sort).toMutableSet()
        val dto = collection.map { roomBookingMapper.get(it) }
        return ResponseEntity.ok(dto)
    }

    override fun patch(
        user: UserEntity?,
        id: Long,
        request: PatchRoomBookingRequest
    ): ResponseEntity<GetRoomBookingResponse> {
        if (user == null)
            throw UnauthorizedException()

        val entity = roomBookingService.get(id)

        val sanitized = if (!user.hasPermission(Permission.MANAGE_ROOM_BOOKINGS)) {
            if (entity.user != user)
                throw ForbiddenException()

            request.copy(userId = null)
        } else request

        val updated = roomBookingService.patch(id, sanitized)
        val dto = roomBookingMapper.get(updated)
        return ResponseEntity.ok(dto)
    }

    override fun delete(
        user: UserEntity?,
        id: Long
    ): ResponseEntity<Void> {
        if (user == null)
            throw UnauthorizedException()

        val entity = roomBookingService.get(id)

        if (!user.hasPermission(Permission.MANAGE_ROOM_BOOKINGS))
            if (entity.user != user)
                throw ForbiddenException()

        roomBookingService.delete(id)
        return ResponseEntity.noContent().build()
    }

}