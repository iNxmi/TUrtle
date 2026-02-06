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

private const val ENDPOINT = "/api/room-bookings"

@RestController
@RequestMapping(ENDPOINT)
class RoomBookingController(
    private val roomBookingService: RoomBookingService
) : CreateController<RoomBookingEntity, CreateRoomBookingRequest, GetRoomBookingResponse>,
    GetController<RoomBookingEntity, Long, GetRoomBookingResponse>,
    PatchController<RoomBookingEntity, PatchRoomBookingRequest, GetRoomBookingResponse>,
    DeleteController<RoomBookingEntity> {

    private val maxTitleLength = 64
    private val maxDescriptionLength = 2048

    override fun create(
        user: UserEntity?,

        request: CreateRoomBookingRequest,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetRoomBookingResponse> {
        if (user == null)
            throw HttpException.Unauthorized()

        var userId = request.userId
        if (!user.hasPermission(Permission.MANAGE_ROOM_BOOKINGS))
            userId = user.id

        if (request.title.isBlank() || request.title.length > maxTitleLength)
            throw HttpException.BadRequest("Title cannot be blank or exceed $maxTitleLength characters.")

        if (request.description.length > maxDescriptionLength)
            throw HttpException.BadRequest("Description cannot exceed $maxDescriptionLength characters.")

        if (request.start == request.end)
            throw HttpException.BadRequest("Start '${request.start}' cannot be the same as end '${request.end}'.")

        if (request.start.isAfter(request.end))
            throw HttpException.BadRequest("Start '${request.start}' cannot be after end '${request.end}'.")

        if (roomBookingService.getAllOverlapping(request.start, request.end, -1).isNotEmpty())
            throw HttpException.Conflict("Room is already booked from '${request.start}' to '${request.end}'.")

        val entity = roomBookingService.create(
            userId = userId,
            title = request.title,
            start = request.start,
            end = request.end,
            description = request.description,
            accessibility = request.accessibility,
            whitelistIds = request.whitelist ?: setOf()
        )

        val location = URI.create("$ENDPOINT/${entity.id}")
        val dto = GetRoomBookingResponse(entity)
        return ResponseEntity.created(location).body(dto)
    }

    override fun get(
        user: UserEntity?,

        variable: Long,

        httpRequest: HttpServletRequest,
        httpResponse: HttpServletResponse
    ): ResponseEntity<GetRoomBookingResponse> {
        val entity = roomBookingService.getById(variable)
            ?: throw HttpException.NotFound()

        val dto = GetRoomBookingResponse(entity)
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
            val dto = page.map { GetRoomBookingResponse(it) }
            return ResponseEntity.ok(dto)
        }

        val collection =
            roomBookingService.getAll(rsql = rsql, sort = sort, specification = specification).toMutableSet()
        val dto = collection.map { GetRoomBookingResponse(it) }
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

        val entity = roomBookingService.getById(id)
            ?: throw HttpException.NotFound()

        var userId: Long? = null
        if (user.hasPermission(Permission.MANAGE_ROOM_BOOKINGS))
            userId = request.userId

        if (request.title != null)
            if (request.title.isBlank() || request.title.length > maxTitleLength)
                throw HttpException.BadRequest("Title cannot be blank or exceed $maxTitleLength characters.")

        if (request.description != null)
            if (request.description.length > maxDescriptionLength)
                throw HttpException.BadRequest("Description cannot exceed $maxDescriptionLength characters.")

        if (request.start != null && request.end != null) {
            if (request.start.isAfter(request.end))
                throw HttpException.BadRequest("Start '${request.start}' cannot be after end '${request.end}'.")

            if (request.start == request.end)
                throw HttpException.BadRequest("Start '${request.start}' cannot be the same as end '${request.end}'.")

            if (roomBookingService.getAllOverlapping(request.start, request.end, id).isNotEmpty())
                throw HttpException.Conflict("Room is already booked from start '${request.start}' to end '${request.end}'.")
        }

        if (request.start != null && request.end == null && request.start.isAfter(entity.end))
            throw HttpException.BadRequest("Start '${request.start}' cannot be after end '${entity.end}'.")

        if (request.start == null && request.end != null && request.end.isBefore(entity.start))
            throw HttpException.BadRequest("End '${request.end}' cannot be before '${entity.start}'.")

        val updated = roomBookingService.patch(
            id = id,
            userId = userId,
            title = request.title,
            start = request.start,
            end = request.end,
            description = request.description,
            accessibility = request.accessibility,
            whitelistIds = request.whitelist
        )

        val dto = GetRoomBookingResponse(updated)
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

        val entity = roomBookingService.getById(id)
            ?: throw HttpException.NotFound()

        if (!user.hasPermission(Permission.MANAGE_ROOM_BOOKINGS))
            if (entity.user != user)
                throw HttpException.Forbidden()

        roomBookingService.delete(id)
        return ResponseEntity.noContent().build()
    }

}