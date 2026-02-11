package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "item_bookings")
class ItemBookingEntity(

    @Id @GeneratedValue
    override val id: Long = 0,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: UserEntity,

    @ManyToOne
    @JoinColumn(name = "item_id")
    var item: ItemEntity,

    @Column(name = "start_time")
    var start: Instant,

    @Column(name = "end_time")
    var end: Instant,

    var collectedAt: Instant? = null,

    var returnedAt: Instant? = null,

    @Enumerated(EnumType.STRING)
    var status: Status,

    //Instant.MIN will be replaced by createdAt in prePersist()
    override var updatedAt: Instant = Instant.MIN,

    @Column(updatable = false)
    override val createdAt: Instant = Instant.now()

) : CRUDEntity {

    enum class Status {
        REQUESTED,
        APPROVED,
        REJECTED,
        CANCELLED,
        PENDING_COLLECTION,
        COLLECTED,
        PENDING_RETURN,
        RETURNED,
        COMPLETED
    }

//    enum class Status {
//        REQUESTED,
//        APPROVED,
//        REJECTED,
//        CANCELLED,
//        COMPLETED
//    }

    @PrePersist
    fun prePersist() {
        updatedAt = createdAt
    }

    @PreUpdate
    fun preUpdate() {
        updatedAt = Instant.now()
    }

    override fun snapshot() = ItemBookingEntity(
        id = id,
        user = user,
        item = item,
        start = start,
        end = end,
        status = status,
        collectedAt = collectedAt,
        returnedAt = returnedAt,
        updatedAt = updatedAt,
        createdAt = createdAt
    )

}