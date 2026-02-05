package de.csw.turtle.api.repository

import de.csw.turtle.api.entity.ItemBookingEntity
import de.csw.turtle.api.entity.ItemEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.Instant

interface ItemBookingRepository : CRUDRepository<ItemBookingEntity>{
    @Query(
        """
        SELECT r FROM ItemBookingEntity r
        WHERE ((r.start >= :start AND r.start <= :end)
        OR    (r.end <= :end AND r.end >= :start))
        AND   (r.item = :item)
        AND   (r.id != :id)
    """)
    fun findAllOverlapping(
        @Param("start") start: Instant,
        @Param("end") end: Instant,
        @Param("item") item: ItemEntity,
        @Param("id") id: Long
    ): Set<ItemBookingEntity>

    @Query("""
        SELECT r FROM ItemBookingEntity r
        WHERE (r.start <= :now AND r.end >= :now)
        AND   (r.item.id = :itemId)
        AND (r.item.locker.id = :lockerId)
    """)
    fun findCurrent(
        @Param("now") now: Instant,
        @Param("itemId") itemId: Long,
        @Param("lockerId") lockerId: Long
    ): ItemBookingEntity?
}