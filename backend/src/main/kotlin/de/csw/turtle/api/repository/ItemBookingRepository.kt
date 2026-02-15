package de.csw.turtle.api.repository

import de.csw.turtle.api.entity.ItemBookingEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.Instant

interface ItemBookingRepository : CRUDRepository<ItemBookingEntity> {
    @Query(
        """
        SELECT r FROM ItemBookingEntity r
        WHERE ((r.start >= :start AND r.start <= :end)
        OR    (r.end <= :end AND r.end >= :start))
        AND   (r.item.id = :itemId)
        AND   (r.id != :id)
    """
    )
    fun findAllOverlapping(
        @Param("start") start: Instant,
        @Param("end") end: Instant,
        @Param("item") itemId: Long,
        @Param("id") id: Long
    ): Set<ItemBookingEntity>

    @Query(
        """
        SELECT r FROM ItemBookingEntity r
        WHERE (r.start <= :now AND r.end >= :now)
        AND   (r.user.id = :userId)
        AND   (r.item.locker.id = :lockerId)
    """
    )
    fun findCurrent(
        @Param("now") now: Instant,
        @Param("userId") userId: Long,
        @Param("lockerId") lockerId: Long
    ): Set<ItemBookingEntity>
}