package de.csw.turtle.api.repository

import de.csw.turtle.api.entity.RoomBookingEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.Instant

interface RoomBookingRepository : CRUDRepository<RoomBookingEntity> {

    @Query("""
        SELECT r FROM RoomBookingEntity r
        WHERE (r.startTime >= :start AND r.startTime <= :end)
        OR    (r.endTime <= :end AND r.endTime >= :start)
    """)
    fun findAllOverlapping(
        @Param("start") start: Instant,
        @Param("end") end: Instant
    ): Set<RoomBookingEntity>

}