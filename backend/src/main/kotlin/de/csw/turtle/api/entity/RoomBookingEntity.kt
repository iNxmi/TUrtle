package de.csw.turtle.api.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "room_bookings")
data class RoomBookingEntity(

    var title: String,

    var startTime: Instant,

    var endTime: Instant,

    @Column(columnDefinition = "TEXT")
    var description: String,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    var creator: UserEntity,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "room_bookings_whitelist",
        joinColumns = [JoinColumn(name = "room_booking_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    var whitelist: MutableSet<UserEntity>? = null

) : CRUDEntity()