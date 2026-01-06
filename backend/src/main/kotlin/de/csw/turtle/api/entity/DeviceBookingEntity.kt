package de.csw.turtle.api.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.time.Instant

@Entity
@Table(name = "device_bookings")
class DeviceBookingEntity(

    @Column(name = "start_time")
    var start: Instant,

    @Column(name = "end_time")
    var end: Instant,

    @ManyToOne
    @JoinColumn(name = "device_id")
    var device: DeviceEntity,

    @ManyToOne
    @JoinColumn(name = "user_id")
    var user: UserEntity

) : CRUDEntity()