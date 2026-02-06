package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "room_bookings")
class RoomBookingEntity(

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: UserEntity,

    var title: String,

    @Column(name = "start_time")
    var start: Instant,

    @Column(name = "end_time")
    var end: Instant,

    @Column(columnDefinition = "TEXT")
    var description: String,

    @Enumerated(EnumType.STRING)
    var accessibility: Accessibility = Accessibility.LOCKED,

    @ManyToMany
    @JoinTable(
        name = "room_booking_whitelist",
        joinColumns = [JoinColumn(name = "room_booking_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    val whitelist: MutableSet<UserEntity> = mutableSetOf(),

    @Enumerated(EnumType.STRING)
    var status: Status

) : CRUDEntity() {

    enum class Accessibility {
        LOCKED,
        WHITELIST,
        UNLOCKED
    }

    enum class Status {
        REQUESTED,
        APPROVED,
        REJECTED,
        CANCELLED,
        COMPLETED
    }

}