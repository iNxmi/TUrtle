package de.csw.turtle.api.service

import de.csw.turtle.api.dto.create.CreateRoomBookingRequest
import de.csw.turtle.api.dto.get.GetRoomBookingResponse
import de.csw.turtle.api.dto.patch.PatchRoomBookingRequest
import de.csw.turtle.api.entity.RoomBookingEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.mapper.RoomBookingMapper
import de.csw.turtle.api.repository.RoomBookingRepository
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class RoomBookingService(
    override val repository: RoomBookingRepository,
    override val mapper: RoomBookingMapper
) : CRUDService<RoomBookingEntity, CreateRoomBookingRequest, GetRoomBookingResponse, PatchRoomBookingRequest>("RoomBooking") {

    private val maxTitleLength = 64
    private val maxDescriptionLength = 2048

    override fun create(request: CreateRoomBookingRequest): RoomBookingEntity {
        if (request.title.isBlank() || request.title.length > maxTitleLength)
            throw HttpException.BadRequest("Title cannot be blank or exceed $maxTitleLength characters.")

        if(request.description.length > maxDescriptionLength)
            throw HttpException.BadRequest("Description cannot exceed $maxDescriptionLength characters.")

        if (request.start == request.end)
            throw HttpException.BadRequest("Start '${request.start}' cannot be the same as end '${request.end}'.")

        if (request.start.isAfter(request.end))
            throw HttpException.BadRequest("Start '${request.start}' cannot be after end '${request.end}'.")

        if (getAllOverlapping(request.start, request.end, -1).isNotEmpty())
            throw HttpException.Conflict("Room is already booked from '${request.start}' to '${request.end}'.")

        return super.create(request)
    }

    override fun patch(id: Long, request: PatchRoomBookingRequest): RoomBookingEntity {
        val original = get(id)

        if (request.title != null)
            if (request.title.isBlank() || request.title.length > maxTitleLength)
                throw HttpException.BadRequest("Title cannot be blank or exceed $maxTitleLength characters.")

        if(request.description != null)
            if(request.description.length > maxDescriptionLength)
                throw HttpException.BadRequest("Description cannot exceed $maxDescriptionLength characters.")

        if (request.start != null && request.end != null) {
            if (request.start.isAfter(request.end)) {
                throw HttpException.BadRequest("Start '${request.start}' cannot be after end '${request.end}'.")
            } else if (request.start == request.end) {
                throw HttpException.BadRequest("Start '${request.start}' cannot be the same as end '${request.end}'.")
            } else if (getAllOverlapping(request.start, request.end, id).isNotEmpty()) {
                throw HttpException.Conflict("Room is already booked from start '${request.start}' to end '${request.end}'.")
            }
        }

        if (request.start != null && request.end == null && request.start.isAfter(original.end))
            throw HttpException.BadRequest("Start '${request.start}' cannot be after end '${original.end}'.")

        if (request.start == null && request.end != null && request.end.isBefore(original.start))
            throw HttpException.BadRequest("End '${request.end}' cannot be before '${original.start}'.")

        return super.patch(id, request)
    }

    fun getAllOverlapping(start: Instant, end: Instant, id: Long): Set<RoomBookingEntity> =
        repository.findAllOverlapping(start, end, id)

    fun getCurrent(): RoomBookingEntity? = repository.findCurrent(Instant.now())

}
