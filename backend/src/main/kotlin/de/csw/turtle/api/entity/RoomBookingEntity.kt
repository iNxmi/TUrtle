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
    var creator: UserEntity,

    var enableWhitelist: Boolean,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "room_booking_whitelist",
        joinColumns = [JoinColumn(name = "room_booking_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    val whitelistedUsers: MutableSet<UserEntity> = mutableSetOf()

) : CRUDEntity()