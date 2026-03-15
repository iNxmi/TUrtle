package de.csw.turtle.api.service

import de.csw.turtle.api.entity.ConfigurationEntity
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
    private val itemRepository: ItemRepository,
    private val configurationService: ConfigurationService
) : CRUDService<ItemBookingEntity>() {

    fun getAllOverlapping(start: Instant, end: Instant, itemId: Long, id: Long): Set<ItemBookingEntity> =
        repository.findAllOverlapping(start, end, itemId, id)

    fun getCurrent(userId: Long, lockerId: Long): Set<ItemBookingEntity> =
        repository.findCurrent(Instant.now(), userId, lockerId)

    // region Validation helpers

    private fun requireUserExists(userId: Long) {
        if (!userRepository.existsById(userId))
            throw HttpException.BadRequest("User with ID '$userId' does not exist.")
    }

    private fun requireItemExists(itemId: Long) {
        if (!itemRepository.existsById(itemId))
            throw HttpException.BadRequest("Item with ID '$itemId' does not exist.")
    }

    private fun validateDateRange(start: Instant, end: Instant) {
        if (start == end)
            throw HttpException.BadRequest("Start '$start' cannot be the same as end '$end'.")
        if (start.isAfter(end))
            throw HttpException.BadRequest("Start '$start' cannot be after end '$end'.")
    }

    private fun validateNoItemOverlap(start: Instant, end: Instant, itemId: Long, excludedId: Long = -1L) {
        if (getAllOverlapping(start, end, itemId, excludedId).isNotEmpty())
            throw HttpException.Conflict("Item with ID '$itemId' is already booked between '$start' and '$end'")
    }

    private fun validateMaxSimultaneousBookings(userId: Long, start: Instant, end: Instant, excludedId: Long = -1L) {
        val maxSimultaneous = configurationService.getTyped<Int>(ConfigurationEntity.Key.ITEM_BOOKING_MAX_SIMULTANEOUS)
        val overlappingCount = repository.findAllOverlappingForUser(userId, start, end, excludedId).size

        if (overlappingCount + 1 > maxSimultaneous) {
            throw HttpException.Conflict("User cannot have more than $maxSimultaneous items simultaneously booked.")
        }
    }

    // endregion

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

        val user = userRepository.findById(userId)
            .orElseThrow { HttpException.BadRequest("User with ID '$userId' does not exist.") }

        val item = itemRepository.findById(itemId)
            .orElseThrow { HttpException.BadRequest("Item with ID '$itemId' does not exist.") }

        validateDateRange(start, end)
        validateNoItemOverlap(start, end, itemId)
        validateMaxSimultaneousBookings(userId, start, end)

        val entity = ItemBookingEntity(
            user = user,
            item = item,
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

        userId?.let { requireUserExists(it) }
        itemId?.let { requireItemExists(it) }

        val effectiveUserId = userId ?: entity.user.id
        val effectiveItemId = itemId ?: entity.item.id
        val effectiveStart = start ?: entity.start
        val effectiveEnd = end ?: entity.end

        if (start != null || end != null) {
            validateDateRange(effectiveStart, effectiveEnd)
            validateNoItemOverlap(effectiveStart, effectiveEnd, effectiveItemId, id)
            validateMaxSimultaneousBookings(effectiveUserId, effectiveStart, effectiveEnd, id)
        }

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