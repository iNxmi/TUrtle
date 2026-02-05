package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "item_bookings")
class ItemBookingEntity(

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: UserEntity,

    @Column(name = "start_time")
    var start: Instant,

    @Column(name = "end_time")
    var end: Instant,

    @ManyToOne
    @JoinColumn(name = "item_id")
    var item: ItemEntity,

    @Enumerated(EnumType.STRING)
    var status: Status = Status.RESERVED

) : CRUDEntity() {

    enum class Status {
        RESERVED,
        COLLECTION_READY,
        ITEM_COLLECTED,
        RESERVATION_ENDED,
        ITEM_RETURNED,
        CANCELLED
    }

}