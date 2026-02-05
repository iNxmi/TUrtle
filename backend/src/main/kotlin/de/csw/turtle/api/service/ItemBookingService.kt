package de.csw.turtle.api.service

import de.csw.turtle.api.dto.create.CreateItemBookingRequest
import de.csw.turtle.api.dto.get.GetItemBookingResponse
import de.csw.turtle.api.dto.patch.PatchItemBookingRequest
import de.csw.turtle.api.entity.ItemBookingEntity
import de.csw.turtle.api.entity.ItemEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.mapper.ItemBookingMapper
import de.csw.turtle.api.repository.ItemBookingRepository
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class ItemBookingService(
    override val repository: ItemBookingRepository,
    override val mapper: ItemBookingMapper,
    private val itemService: ItemService
): CRUDService<ItemBookingEntity, CreateItemBookingRequest, GetItemBookingResponse, PatchItemBookingRequest>("DeviceBooking"){

    override fun create(request: CreateItemBookingRequest): ItemBookingEntity {

        if (request.start == request.end)
            throw HttpException.BadRequest("Start '${request.start}' cannot be the same as end '${request.end}'.")

        if (request.start.isAfter(request.end))
            throw HttpException.BadRequest("Start '${request.start}' cannot be after end '${request.end}'.")

        if(getAllOverlapping(request.start, request.end, request.itemId, -1).isNotEmpty()) {
            throw HttpException.Conflict("Item with ID '${request.itemId}' is already booked between '${request.start}' and '${request.end}'")
        }

        return super.create(request)
    }

    override fun patch(id: Long, request: PatchItemBookingRequest): ItemBookingEntity {
        val original = get(id)

        if (request.start != null && request.end != null) {
            if (request.start.isAfter(request.end)) {
                throw HttpException.BadRequest("Start '${request.start}' cannot be after end '${request.end}'.")
            } else if (request.start == request.end) {
                throw HttpException.BadRequest("Start '${request.start}' cannot be the same as end '${request.end}'.")
            } else if(request.itemId != null){
                if(getAllOverlapping(request.start,request.end,request.itemId, id).isNotEmpty()){
                    //Eventuell Probleme wenn man versucht die Zeit so zu patchen, dass es mit der alten überlappt?
                    //Nochmal überlegen wenn request.itemId == original.item.id
                    throw HttpException.Conflict("Item with ID '${request.itemId}' is already booked between '${request.start}' and '${request.end}'")
                }
            } else {
                if(getAllOverlapping(request.start,request.end,original.item.id, id).isNotEmpty()){
                    throw HttpException.Conflict("Item with ID '${original.item.id}' is already booked between '${request.start}' and '${request.end}'")
                }
            }

        }

        if (request.start != null && request.end == null && request.start.isAfter(original.end))
            throw HttpException.BadRequest("Start '${request.start}' cannot be after end '${original.end}'.")

        if (request.start == null && request.end != null && request.end.isBefore(original.start))
            throw HttpException.BadRequest("End '${request.end}' cannot be before '${original.start}'.")

        return super.patch(id, request)
    }

    fun getAllOverlapping(start: Instant, end: Instant,item: Long , id: Long): Set<ItemBookingEntity> =
        repository.findAllOverlapping(start, end, itemService.get(item), id)

    fun getCurrent(item: Long): ItemBookingEntity? =
        repository.findCurrent(Instant.now(),item)

}