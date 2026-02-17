package de.csw.turtle.api.service

import de.csw.turtle.api.entity.RoomBookingEntity
import de.csw.turtle.api.entity.RoomBookingEntity.Accessibility
import de.csw.turtle.api.event.CreatedRoomBookingEvent
import de.csw.turtle.api.event.PatchedRoomBookingEvent
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.repository.RoomBookingRepository
import de.csw.turtle.api.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.context.ApplicationEventPublisher
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class RoomBookingService(
    override val repository: RoomBookingRepository,
    private val userRepository: UserRepository,
    private val eventPublisher: ApplicationEventPublisher
) : CRUDService<RoomBookingEntity>() {

    fun getAllOverlapping(start: Instant, end: Instant, id: Long): Set<RoomBookingEntity> =
        repository.findAllOverlapping(start, end, id)

    fun getCurrent(): RoomBookingEntity? = repository.findCurrent(Instant.now())

    private val maxTitleLength = 64
    private val maxDescriptionLength = 2048

    @Transactional
    fun create(
        userId: Long,
        title: String,
        start: Instant,
        end: Instant,
        description: String,
        accessibility: Accessibility,
        whitelistedUserIds: Set<Long>,
        status: RoomBookingEntity.Status
    ): RoomBookingEntity {

        if(!userRepository.existsById(userId))
            throw HttpException.BadRequest("User with id '$userId' does not exist.")

        if (title.isBlank() || title.length > maxTitleLength)
            throw HttpException.BadRequest("Title cannot be blank or exceed $maxTitleLength characters.")

        if (description.isBlank() || description.length > maxDescriptionLength)
            throw HttpException.BadRequest("Description cannot be blank or exceed $maxDescriptionLength characters.")

        if (start.isBefore(Instant.now()))
            throw HttpException.BadRequest("Start cannot be in the past.")

        if (start == end)
            throw HttpException.BadRequest("Start '${start}' cannot be the same as end '${end}'.")

        if (start.isAfter(end))
            throw HttpException.BadRequest("Start '${start}' cannot be after end '${end}'.")

        if (repository.findAllOverlapping(start, end, -1).isNotEmpty())
            throw HttpException.Conflict("Room is already booked from '${start}' to '${end}'.")

        if(!whitelistedUserIds.isEmpty()){
            for (userId in whitelistedUserIds) {
                if(!userRepository.existsById(userId))
                    throw HttpException.BadRequest("Whitelisted user with id '$userId' does not exist.")
            }
        }

        val entity = RoomBookingEntity(
            user = userRepository.findById(userId).get(),
            title = title,
            start = start,
            end = end,
            description = description,
            accessibility = accessibility,
            whitelistedUsers = whitelistedUserIds.map { userRepository.findById(it).get() }.toMutableSet(),
            status = status
        )

        val saved = repository.save(entity)

        eventPublisher.publishEvent(CreatedRoomBookingEvent(saved))

        return saved
    }

    @Transactional
    fun patch(
        id: Long,
        userId: Long? = null,
        title: String? = null,
        start: Instant? = null,
        end: Instant? = null,
        description: String? = null,
        accessibility: Accessibility? = null,
        whitelistIds: Set<Long>? = null,
        status: RoomBookingEntity.Status? = null
    ): RoomBookingEntity {
        val entity = repository.findById(id).get()

        if (title != null)
            if (title.isBlank() || title.length > maxTitleLength)
                throw HttpException.BadRequest("Title cannot be blank or exceed $maxTitleLength characters.")

        if (description != null)
            if (description.length > maxDescriptionLength)
                throw HttpException.BadRequest("Description cannot exceed $maxDescriptionLength characters.")

        if (start != null && end != null) {
            if (start.isAfter(end))
                throw HttpException.BadRequest("Start '${start}' cannot be after end '${end}'.")

            if (start == end)
                throw HttpException.BadRequest("Start '${start}' cannot be the same as end '${end}'.")

            if (repository.findAllOverlapping(start, end, id).isNotEmpty())
                throw HttpException.Conflict("Room is already booked from start '${start}' to end '${end}'.")
        }

        if (start != null && end == null && start.isAfter(entity.end))
            throw HttpException.BadRequest("Start '${start}' cannot be after end '${entity.end}'.")

        if (start == null && end != null && end.isBefore(entity.start))
            throw HttpException.BadRequest("End '${end}' cannot be before '${entity.start}'.")

        val pre = entity.snapshot()

        userId?.let { entity.user = userRepository.findById(it).get() }
        title?.let { entity.title = it }
        start?.let { entity.start = it }
        end?.let { entity.end = it }
        description?.let { entity.description = it }
        accessibility?.let { entity.accessibility = it }
        whitelistIds?.let { ids ->
            val users = ids.map { userRepository.findById(it).get() }

            entity.whitelistedUsers.clear()
            entity.whitelistedUsers.addAll(users)
        }
        status?.let { entity.status = it }

        val post = repository.save(entity)

        val event = PatchedRoomBookingEvent(pre = pre, post = post)
        eventPublisher.publishEvent(event)

        return post
    }

}
