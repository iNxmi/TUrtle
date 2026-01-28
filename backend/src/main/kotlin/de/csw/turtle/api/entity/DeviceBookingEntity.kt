package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "device_bookings")
class DeviceBookingEntity(

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: UserEntity,

    @Column(name = "start_time")
    var start: Instant,

    @Column(name = "end_time")
    var end: Instant,

    @ManyToOne
    @JoinColumn(name = "device_id")
    var device: DeviceEntity,

    @Enumerated(EnumType.STRING)
    var status: Status = Status.RESERVED

) : CRUDEntity() {

    enum class Status {
        RESERVED,
        COLLECTION_READY,
        DEVICE_COLLECTED,
        RESERVATION_ENDED,
        DEVICE_RETURNED,
        CANCELLED
    }

}