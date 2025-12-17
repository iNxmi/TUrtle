package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "room_bookings")
class RoomBookingEntity(

    var title: String,

    @Column(name = "start_time")
    var start: Instant,

    @Column(name = "end_time")
    var end: Instant,

    @Column(columnDefinition = "TEXT")
    var description: String,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var creator: UserEntity,

    @Enumerated(EnumType.STRING)
    var accessibility: Accessibility = Accessibility.LOCKED,

    @ManyToMany
    @JoinTable(
        name = "room_booking_whitelist",
        joinColumns = [JoinColumn(name = "room_booking_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    val whitelist: MutableSet<UserEntity> = mutableSetOf()

) : CRUDEntity() {

    enum class Accessibility {
        LOCKED, WHITELIST, UNLOCKED
    }

}