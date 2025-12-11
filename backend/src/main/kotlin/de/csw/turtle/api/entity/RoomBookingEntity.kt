package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "room_bookings")
data class RoomBookingEntity(

    var title: String,

    @Column(name = "start_time")
    var start: Instant,

    @Column(name = "end_time")
    var end: Instant,

    @Column(columnDefinition = "TEXT")
    var description: String,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    var creator: UserEntity

) : CRUDEntity()