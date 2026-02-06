package de.csw.turtle.api.service

import de.csw.turtle.api.entity.ItemBookingEntity
import de.csw.turtle.api.repository.ItemBookingRepository
import de.csw.turtle.api.repository.ItemRepository
import de.csw.turtle.api.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class ItemBookingService(
    override val repository: ItemBookingRepository,
    private val userRepository: UserRepository,
    private val itemRepository: ItemRepository
) : CRUDService<ItemBookingEntity>() {

    fun getAllOverlapping(start: Instant, end: Instant, item: Long, id: Long): Set<ItemBookingEntity> =
        repository.findAllOverlapping(start, end, itemService.getById(item), id)

    fun getCurrent(userId: Long, lockerId: Long): Set<ItemBookingEntity> =
        repository.findCurrent(Instant.now(), userId, lockerId)

    fun create(
        userId: Long,
        itemId: Long,
        start: Instant,
        end: Instant,
        status: ItemBookingEntity.Status
    ): ItemBookingEntity {
        val entity = ItemBookingEntity(
            user = userRepository.findById(userId).get(),
            item = itemRepository.findById(itemId).get(),
            start = start,
            end = end,
            status = status
        )

        return repository.save(entity)
    }

    fun patch(
        id: Long,
        userId: Long? = null,
        itemId: Long? = null,
        start: Instant? = null,
        end: Instant? = null,
        status: ItemBookingEntity.Status? = null,
    ): ItemBookingEntity {
        val original = get(id)



        return super.patch(id, request)
    }


}