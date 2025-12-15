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

    var enableWhitelist: Boolean,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var creator: UserEntity,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "room_bookings_whitelist",
        joinColumns = [JoinColumn(name = "room_booking_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    var whitelist: MutableSet<UserEntity> = mutableSetOf()

) : CRUDEntity() {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is RoomBookingEntity) return false
        return id != 0L && id == other.id
    }

    override fun hashCode(): Int = javaClass.hashCode()

    override fun toString(): String {
        return "RoomBookingEntity(id=$id, title='$title', start=$start, end=$end)"
    }
}