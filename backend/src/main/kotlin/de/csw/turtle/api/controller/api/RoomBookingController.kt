package de.csw.turtle.api.controller.api

import de.csw.turtle.api.Permission
import de.csw.turtle.api.controller.CreateController
import de.csw.turtle.api.controller.DeleteController
import de.csw.turtle.api.controller.GetController
import de.csw.turtle.api.controller.PatchController
import de.csw.turtle.api.dto.create.CreateRoomBookingRequest
import de.csw.turtle.api.dto.get.GetRoomBookingResponse
import de.csw.turtle.api.dto.patch.PatchRoomBookingRequest
import de.csw.turtle.api.entity.RoomBookingEntity
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.mapper.RoomBookingMapper
import de.csw.turtle.api.service.RoomBookingService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.domain.Specification
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
    GetController<RoomBookingEntity, Long, GetRoomBookingResponse>,
    PatchController<RoomBookingEntity, PatchRoomBookingRequest, GetRoomBookingResponse>,
    DeleteController<RoomBookingEntity> {

    override fun create(
        user: UserEntity?,

        request: CreateRoomBookingRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetRoomBookingResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        val sanitized = if (user.hasPermission(Permission.MANAGE_ROOM_BOOKINGS)) {
            request
        } else {
            CreateRoomBookingRequest(
                userId = user.id,
                title = request.title,
                description = request.description,
                start = request.start,
                end = request.end,
                accessibility = request.accessibility,
                whitelist = request.whitelist
            )
        }

        val entity = roomBookingService.create(sanitized)
        val location = URI.create("/api/room-bookings/${entity.id}")
        val dto = roomBookingMapper.get(entity)
        return ResponseEntity.created(location).body(dto)
    }

    override fun get(
        user: UserEntity?,

        variable: Long,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetRoomBookingResponse> {
        val entity = roomBookingService.get(variable)
        val dto = roomBookingMapper.get(entity)
        return ResponseEntity.ok(dto)
    }

    override fun getCollection(
        user: UserEntity?,

        rsql: String?,
        pageNumber: Int?,
        pageSize: Int,
        sortProperty: String?,
        sortDirection: Sort.Direction,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<Any> {
        if (user == null)
            throw HttpException.Unauthorized()

        val sort = sortProperty?.let {
            Sort.by(sortDirection, sortProperty)
        } ?: Sort.unsorted()

        val specification: Specification<RoomBookingEntity> =
            if (user.hasPermission(Permission.MANAGE_ROOM_BOOKINGS)) {
                Specification.unrestricted()
            } else Specification { root, _, builder ->
                builder.equal(root.get<UserEntity>("user"), user)
            }

        if (pageNumber != null) {
            val pageable = PageRequest.of(pageNumber, pageSize, sort)
            val page = roomBookingService.getPage(rsql = rsql, pageable = pageable, specification = specification)
            val dto = page.map { roomBookingMapper.get(it) }
            return ResponseEntity.ok(dto)
        }

        val collection =
            roomBookingService.getAll(rsql = rsql, sort = sort, specification = specification).toMutableSet()
        val dto = collection.map { roomBookingMapper.get(it) }
        return ResponseEntity.ok(dto)
    }

    override fun patch(
        user: UserEntity?,

        id: Long,
        request: PatchRoomBookingRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetRoomBookingResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        val entity = roomBookingService.get(id)

        val sanitized = if (user.hasPermission(Permission.MANAGE_ROOM_BOOKINGS)) {
            request
        } else if (entity.user == user) {
            PatchRoomBookingRequest(
                title = request.title,
                description = request.description,
                start = request.start,
                end = request.end,
                accessibility = request.accessibility,
                whitelist = request.whitelist
            )
        } else throw HttpException.Forbidden()

        val updated = roomBookingService.patch(id, sanitized)
        val dto = roomBookingMapper.get(updated)
        return ResponseEntity.ok(dto)
    }

    override fun delete(
        user: UserEntity?,

        id: Long,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<Void> {
        if (user == null)
            throw HttpException.Unauthorized()

        val entity = roomBookingService.get(id)

        if (!user.hasPermission(Permission.MANAGE_ROOM_BOOKINGS))
            if (entity.user != user)
                throw HttpException.Forbidden()

        roomBookingService.delete(id)
        return ResponseEntity.noContent().build()
    }

}