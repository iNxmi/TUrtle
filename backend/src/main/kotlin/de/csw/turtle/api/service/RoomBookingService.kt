package de.csw.turtle.api.service

import de.csw.turtle.api.dto.create.CreateRoomBookingRequest
import de.csw.turtle.api.dto.get.GetRoomBookingResponse
import de.csw.turtle.api.dto.patch.PatchRoomBookingRequest
import de.csw.turtle.api.entity.RoomBookingEntity
import de.csw.turtle.api.exception.BadRequestException
import de.csw.turtle.api.exception.ConflictException
import de.csw.turtle.api.mapper.RoomBookingMapper
import de.csw.turtle.api.repository.RoomBookingRepository
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class RoomBookingService(
    override val repository: RoomBookingRepository,
    override val mapper: RoomBookingMapper
) : CRUDService<RoomBookingEntity, CreateRoomBookingRequest, GetRoomBookingResponse, PatchRoomBookingRequest>("RoomBooking") {

    override fun create(request: CreateRoomBookingRequest): RoomBookingEntity {
        if(request.title.isBlank())
            throw BadRequestException("Title cannot be blank")

        if(request.start.equals(request.end))
            throw BadRequestException("'${request.start}' cannot be the same as '${request.end}'.")

        if(request.start.isAfter(request.end))
            throw BadRequestException("'${request.start}' cannot be after ${request.end}.")

        if(getAllOverlapping(request.start,request.end).isNotEmpty())
            throw ConflictException("Room is already booked from '${request.start}' to '${request.end}'.")

        return super.create(request)
    }

    override fun patch(id: Long, request: PatchRoomBookingRequest): RoomBookingEntity {
        val original = get(id)

        if(request.title != null)
            if(request.title.isBlank())
                throw BadRequestException("Title cannot be blank")

        if(request.start != null && request.start.isAfter(original.end))
            throw BadRequestException("'${request.start}' cannot be after ${original.end}'.")

        if(request.end != null && request.end.isBefore(original.start))
            throw BadRequestException("'${request.end}' cannot be after '${original.start}'.")

        if(request.start != null && request.end != null)
            if(request.start.isAfter(request.end))
                throw BadRequestException("'${request.start}' cannot be after ${request.end}'.")
            else if(request.start.equals(request.end))
                throw BadRequestException("'${request.start}' cannot be the same as '${request.end}'.")
            else if(getAllOverlapping(request.start,request.end).isNotEmpty())
                throw ConflictException("Room is already booked from '${request.start}' to '${request.end}'.")


        return super.patch(id, request)
    }

    fun getAllOverlapping(start: Instant, end: Instant): Set<RoomBookingEntity> {
        return repository.findAllOverlapping(start, end)
    }

}
