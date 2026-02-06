package de.csw.turtle.api.service

import de.csw.turtle.api.entity.RoomBookingEntity
import de.csw.turtle.api.entity.RoomBookingEntity.Accessibility
import de.csw.turtle.api.repository.RoomBookingRepository
import de.csw.turtle.api.repository.UserRepository
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class RoomBookingService(
    override val repository: RoomBookingRepository,
    private val userRepository: UserRepository
) : CRUDService<RoomBookingEntity>() {

    fun getAllOverlapping(start: Instant, end: Instant, id: Long): Set<RoomBookingEntity> =
        repository.findAllOverlapping(start, end, id)

    fun getCurrent(): RoomBookingEntity? = repository.findCurrent(Instant.now())

    fun create(
        userId: Long,
        title: String,
        start: Instant,
        end: Instant,
        description: String,
        accessibility: Accessibility,
        whitelistIds: Set<Long>
    ): RoomBookingEntity {
        val entity = RoomBookingEntity(
            user = userRepository.findById(userId).get(),
            title = title,
            start = start,
            end = end,
            description = description,
            accessibility = accessibility,
            whitelist = whitelistIds.map { userRepository.findById(it).get() }.toMutableSet()
        )

        return repository.save(entity)
    }

    fun patch(
        id: Long,
        userId: Long? = null,
        title: String? = null,
        start: Instant? = null,
        end: Instant? = null,
        description: String? = null,
        accessibility: Accessibility? = null,
        whitelistIds: Set<Long>? = null
    ): RoomBookingEntity {
        val entity = repository.findById(id).get()

        userId?.let { entity.user = userRepository.findById(it).get() }
        title?.let { entity.title = it }
        start?.let { entity.start = it }
        end?.let { entity.end = it }
        description?.let { entity.description = it }
        accessibility?.let { entity.accessibility = it }
        whitelistIds?.let { ids ->
            val users = ids.map { userRepository.findById(it).get() }

            entity.whitelist.clear()
            entity.whitelist.addAll(users)
        }

        return repository.save(entity)
    }

}
