package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "devices")
class DeviceEntity(

    var name: String,

    @Column(columnDefinition = "TEXT")
    var description: String,

    @ManyToOne
    @JoinColumn(name = "category_id")
    var category: DeviceCategoryEntity,

    @ManyToOne
    @JoinColumn(name = "locker_id")
    var locker: LockerEntity,

    var acquiredAt: Instant

) : CRUDEntity()