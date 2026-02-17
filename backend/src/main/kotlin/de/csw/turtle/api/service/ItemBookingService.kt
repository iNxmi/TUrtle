package de.csw.turtle.api.service

import de.csw.turtle.api.entity.ItemBookingEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.repository.ItemBookingRepository
import de.csw.turtle.api.repository.ItemRepository
import de.csw.turtle.api.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class ItemBookingService(
    override val repository: ItemBookingRepository,
    private val userRepository: UserRepository,
    private val itemRepository: ItemRepository
) : CRUDService<ItemBookingEntity>() {

    fun getAllOverlapping(start: Instant, end: Instant, itemId: Long, id: Long): Set<ItemBookingEntity> =
        repository.findAllOverlapping(start, end, itemId, id)

    fun getCurrent(userId: Long, lockerId: Long): Set<ItemBookingEntity> =
        repository.findCurrent(Instant.now(), userId, lockerId)

    @Transactional
    fun create(
        userId: Long,
        itemId: Long,
        start: Instant,
        end: Instant,
        collectedAt: Instant?,
        returnedAt: Instant?,
        status: ItemBookingEntity.Status
    ): ItemBookingEntity {

        if(!userRepository.existsById(userId))
            throw HttpException.BadRequest("User with ID '$userId' does not exist.")

        if(!itemRepository.existsById(itemId))
            throw HttpException.BadRequest("Item with ID '$itemId' does not exist.")

        if (start == end)
            throw HttpException.BadRequest("Start '${start}' cannot be the same as end '${end}'.")

        if (start.isAfter(end))
            throw HttpException.BadRequest("Start '${start}' cannot be after end '${end}'.")

        if (getAllOverlapping(start, end, itemId, -1).isNotEmpty())
            throw HttpException.Conflict("Item with ID '${itemId}' is already booked between '${start}' and '${end}'")

        val entity = ItemBookingEntity(
            user = userRepository.findById(userId).get(),
            item = itemRepository.findById(itemId).get(),
            start = start,
            end = end,
            collectedAt = collectedAt,
            returnedAt = returnedAt,
            status = status
        )

        return repository.save(entity)
    }

    @Transactional
    fun patch(
        id: Long,
        userId: Long? = null,
        itemId: Long? = null,
        start: Instant? = null,
        end: Instant? = null,
        collectedAt: Instant? = null,
        returnedAt: Instant? = null,
        status: ItemBookingEntity.Status? = null,
    ): ItemBookingEntity {
        val entity = repository.findById(id).get()

        if (start != null && end != null) {
            if (start.isAfter(end))
                throw HttpException.BadRequest("Start '${start}' cannot be after end '${end}'.")

            if (start == end)
                throw HttpException.BadRequest("Start '${start}' cannot be the same as end '${end}'.")

            if (itemId != null)
                if (repository.findAllOverlapping(start, end, itemId, id).isNotEmpty())
                    throw HttpException.Conflict("Item with ID '${itemId}' is already booked between '${start}' and '${end}'")

            if (repository.findAllOverlapping(start, end, entity.item.id, id).isNotEmpty())
                throw HttpException.Conflict("Item with ID '${entity.item.id}' is already booked between '${start}' and '${end}'")
        }

        if (start != null && end == null && start.isAfter(entity.end))
            throw HttpException.BadRequest("Start '${start}' cannot be after end '${entity.end}'.")

        if (start == null && end != null && end.isBefore(entity.start))
            throw HttpException.BadRequest("End '${end}' cannot be before '${entity.start}'.")

        userId?.let { entity.user = userRepository.findById(it).get() }
        itemId?.let { entity.item = itemRepository.findById(it).get() }
        start?.let { entity.start = it }
        end?.let { entity.end = it }
        collectedAt?.let { entity.collectedAt = it }
        returnedAt?.let { entity.returnedAt = it }
        status?.let { entity.status = it }

        return repository.save(entity)
    }


}