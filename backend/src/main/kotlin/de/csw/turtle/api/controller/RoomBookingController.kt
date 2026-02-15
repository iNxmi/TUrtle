package de.csw.turtle.api.controller

import de.csw.turtle.api.Permission
import de.csw.turtle.api.dto.create.CreateRoomBookingRequest
import de.csw.turtle.api.dto.get.GetRoomBookingResponse
import de.csw.turtle.api.dto.patch.PatchRoomBookingRequest
import de.csw.turtle.api.entity.RoomBookingEntity
import de.csw.turtle.api.entity.RoomBookingEntity.Status
import de.csw.turtle.api.entity.UserEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.service.RoomBookingService
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.*
import java.net.URI

private const val ENDPOINT = "/api/room-bookings"

@RestController
@RequestMapping(ENDPOINT)
class RoomBookingController(
    private val roomBookingService: RoomBookingService
) : CreateController<RoomBookingEntity, CreateRoomBookingRequest, GetRoomBookingResponse>,
    GetController<RoomBookingEntity, Long, GetRoomBookingResponse>,
    PatchController<RoomBookingEntity, PatchRoomBookingRequest, GetRoomBookingResponse>,
    DeleteController<RoomBookingEntity> {

    @PostMapping
    override fun create(
        @AuthenticationPrincipal user: UserEntity?,

        @RequestBody request: CreateRoomBookingRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetRoomBookingResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.REQUEST_ROOM_BOOKINGS) && user.hasPermission(Permission.MANAGE_ROOM_BOOKINGS))
            throw HttpException.Forbidden()

        var userId = user.id
        var status = Status.REQUESTED
        if (user.hasPermission(Permission.MANAGE_ROOM_BOOKINGS)) {
            request.userId?.let { userId = it }
            request.status?.let { status = it }
        }

        val entity = roomBookingService.create(
            userId = userId,
            title = request.title,
            start = request.start,
            end = request.end,
            description = request.description,
            accessibility = request.accessibility,
            whitelistedUserIds = request.whitelistedUserIds ?: setOf(),
            status = status
        )

        val location = URI.create("$ENDPOINT/${entity.id}")
        val dto = GetRoomBookingResponse(entity)
        return ResponseEntity.created(location).body(dto)
    }

    @GetMapping("/{variable}")
    override fun get(
        @AuthenticationPrincipal user: UserEntity?,

        @PathVariable variable: Long,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetRoomBookingResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        val entity = roomBookingService.getById(variable)
            ?: throw HttpException.NotFound()

        val dto = GetRoomBookingResponse(entity)
        return ResponseEntity.ok(dto)
    }

    @GetMapping
    override fun getCollection(
        @AuthenticationPrincipal user: UserEntity?,

        @RequestParam rsql: String?,
        @RequestParam pageNumber: Int?,
        @RequestParam pageSize: Int,
        @RequestParam sortProperty: String?,
        @RequestParam sortDirection: Sort.Direction,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<Any> {
        if (user == null)
            throw HttpException.Unauthorized()

        val sort = sortProperty?.let {
            Sort.by(sortDirection, sortProperty)
        } ?: Sort.unsorted()

        if (pageNumber != null) {
            val pageable = PageRequest.of(pageNumber, pageSize, sort)
            val page = roomBookingService.getPage(rsql = rsql, pageable = pageable)
            val dto = page.map { GetRoomBookingResponse(it) }
            return ResponseEntity.ok(dto)
        }

        val collection =
            roomBookingService.getAll(rsql = rsql, sort = sort).toMutableSet()
        val dto = collection.map { GetRoomBookingResponse(it) }
        return ResponseEntity.ok(dto)
    }

    @PatchMapping("/{id}")
    override fun patch(
        @AuthenticationPrincipal user: UserEntity?,

        @PathVariable id: Long,
        @RequestBody request: PatchRoomBookingRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetRoomBookingResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        roomBookingService.getById(id)
            ?: throw HttpException.NotFound()

        var userId: Long? = null
        var status: Status? = null
        if (user.hasPermission(Permission.MANAGE_ROOM_BOOKINGS)) {
            userId = request.userId
            status = request.status
        }

        val updated = roomBookingService.patch(
            id = id,
            userId = userId,
            title = request.title,
            start = request.start,
            end = request.end,
            description = request.description,
            accessibility = request.accessibility,
            whitelistIds = request.whitelistedUserIds,
            status = status
        )

        val dto = GetRoomBookingResponse(updated)
        return ResponseEntity.ok(dto)
    }

    @DeleteMapping("/{id}")
    override fun delete(
        @AuthenticationPrincipal user: UserEntity?,

        @PathVariable id: Long,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<Void> {
        if (user == null)
            throw HttpException.Unauthorized()

        if (!user.hasPermission(Permission.MANAGE_ROOM_BOOKINGS))
            throw HttpException.Forbidden()

        roomBookingService.delete(id)
        return ResponseEntity.noContent().build()
    }

}