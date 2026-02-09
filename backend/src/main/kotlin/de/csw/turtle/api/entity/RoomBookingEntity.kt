package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "room_bookings")
class RoomBookingEntity(

    @Id @GeneratedValue
    override val id: Long = 0,

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
        name = "room_bookings_whitelisted_users",
        joinColumns = [JoinColumn(name = "room_booking_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    val whitelistedUsers: MutableSet<UserEntity> = mutableSetOf(),

    @Enumerated(EnumType.STRING)
    var status: Status,

    //Instant.MIN will be replaced by createdAt in prePersist()
    override var updatedAt: Instant = Instant.MIN,

    @Column(updatable = false)
    override val createdAt: Instant = Instant.now()

) : CRUDEntity {

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

    @PrePersist
    fun prePersist() {
        updatedAt = createdAt
    }

    @PreUpdate
    fun preUpdate() {
        updatedAt = Instant.now()
    }

    override fun snapshot() = RoomBookingEntity(
        id = id,
        user = user,
        title = title,
        start = start,
        end = end,
        description = description,
        accessibility = accessibility,
        whitelistedUsers = whitelistedUsers.toMutableSet(),
        status = status,
        updatedAt = updatedAt,
        createdAt = createdAt
    )

}