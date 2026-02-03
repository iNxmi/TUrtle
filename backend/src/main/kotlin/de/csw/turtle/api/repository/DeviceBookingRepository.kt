package de.csw.turtle.api.repository

import de.csw.turtle.api.entity.DeviceBookingEntity
import de.csw.turtle.api.entity.DeviceEntity
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.time.Instant

interface DeviceBookingRepository : CRUDRepository<DeviceBookingEntity>{
    @Query("""
        SELECT r FROM DeviceBookingEntity r
        WHERE ((r.start >= :start AND r.start <= :end)
        OR    (r.end <= :end AND r.end >= :start))
        AND   (r.device = :device)
        AND   (r.id != :id)
    """)
    fun findAllOverlapping(
        @Param("start") start: Instant,
        @Param("end") end: Instant,
        @Param("device") device: DeviceEntity,
        @Param("id") id: Long
    ): Set<DeviceBookingEntity>
}