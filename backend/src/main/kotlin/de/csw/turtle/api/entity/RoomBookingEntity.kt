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
@Table(name = "room_booking")
data class RoomBookingEntity(

    @Column(nullable = false)
    var title: String,

    @Column(nullable = false)
    var start: Instant,

    @Column(nullable = false)
    var end: Instant,

    @Column(nullable = false)
    var description: String,


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    var creator: UserEntity,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_room_bookings",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "room_booking_id")]
    )
    val whitelist: MutableSet<UserEntity>? = null,

    @Id
    @GeneratedValue
    override val id: Long = 0,

    @Column(nullable = false, updatable = false)
    override val createdAt: Instant = Instant.now()

) : CRUDEntity(){

}