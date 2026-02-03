package de.csw.turtle.api.service

import de.csw.turtle.api.dto.create.CreateDeviceBookingRequest
import de.csw.turtle.api.dto.get.GetDeviceBookingResponse
import de.csw.turtle.api.dto.patch.PatchDeviceBookingRequest
import de.csw.turtle.api.entity.DeviceBookingEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.mapper.DeviceBookingMapper
import de.csw.turtle.api.repository.DeviceBookingRepository
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class DeviceBookingService(
    override val repository: DeviceBookingRepository,
    override val mapper: DeviceBookingMapper,
    private val deviceService: DeviceService
): CRUDService<DeviceBookingEntity, CreateDeviceBookingRequest, GetDeviceBookingResponse, PatchDeviceBookingRequest>("DeviceBooking"){

    override fun create(request: CreateDeviceBookingRequest): DeviceBookingEntity {

        if (request.start == request.end)
            throw HttpException.BadRequest("Start '${request.start}' cannot be the same as end '${request.end}'.")

        if (request.start.isAfter(request.end))
            throw HttpException.BadRequest("Start '${request.start}' cannot be after end '${request.end}'.")

        if(getAllOverlapping(request.start, request.end, request.deviceId, -1).isNotEmpty()) {
            throw HttpException.Conflict("Device with ID '${request.deviceId}' is already booked between '${request.start}' and '${request.end}'")
        }

        return super.create(request)
    }

    override fun patch(id: Long, request: PatchDeviceBookingRequest): DeviceBookingEntity {
        val original = get(id)

        if (request.start != null && request.end != null) {
            if (request.start.isAfter(request.end)) {
                throw HttpException.BadRequest("Start '${request.start}' cannot be after end '${request.end}'.")
            } else if (request.start == request.end) {
                throw HttpException.BadRequest("Start '${request.start}' cannot be the same as end '${request.end}'.")
            } else if(request.deviceId != null){
                if(getAllOverlapping(request.start,request.end,request.deviceId, id).isNotEmpty()){
                    //Eventuell Probleme wenn man versucht die Zeit so zu patchen, dass es mit der alten überlappt?
                    //Nochmal überlegen wenn request.deviceId == original.device.id
                    throw HttpException.Conflict("Device with ID '${request.deviceId}' is already booked between '${request.start}' and '${request.end}'")
                }
            } else {
                if(getAllOverlapping(request.start,request.end,original.device.id, id).isNotEmpty()){
                    throw HttpException.Conflict("Device with ID '${original.device.id}' is already booked between '${request.start}' and '${request.end}'")
                }
            }

        }

        if (request.start != null && request.end == null && request.start.isAfter(original.end))
            throw HttpException.BadRequest("Start '${request.start}' cannot be after end '${original.end}'.")

        if (request.start == null && request.end != null && request.end.isBefore(original.start))
            throw HttpException.BadRequest("End '${request.end}' cannot be before '${original.start}'.")

        return super.patch(id, request)
    }

    fun getAllOverlapping(start: Instant, end: Instant,device: Long , id: Long): Set<DeviceBookingEntity> =
        repository.findAllOverlapping(start, end, deviceService.get(device), id)

}