package de.csw.turtle.api.repository

import de.csw.turtle.api.entity.RoomBookingEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.Instant

interface RoomBookingRepository : CRUDRepository<RoomBookingEntity>{
    @Query("""
        SELECT r FROM RoomBookingEntity r
        WHERE ((r.start >= :start AND r.start <= :end)
        OR    (r.end <= :end AND r.end >= :start))
        AND   (r.id != :id)
    """)
    fun findAllOverlapping(
        @Param("start") start: Instant,
        @Param("end") end: Instant,
        @Param("id") id: Long
    ): Set<RoomBookingEntity>

    @Query("""
        SELECT r FROM RoomBookingEntity r
        WHERE (r.start <= :current AND r.end >= :current)
    """)
    fun findAllCurrent(
        @Param("current") current: Instant
    ): Set<RoomBookingEntity>
}