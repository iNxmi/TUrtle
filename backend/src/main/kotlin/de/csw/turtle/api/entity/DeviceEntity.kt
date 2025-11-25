package de.csw.turtle.api.entity

import jakarta.persistence.*

@Entity
data class DeviceEntity(

    @Column(nullable = false)
    var name: String,

    @Column(nullable = false)
    var description: String,

    @Column(nullable = false)
    var inventoryId: String,

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    var category: DeviceCategoryEntity,

    @ManyToOne
    @JoinColumn(name = "locker_id", nullable = false)
    var locker: LockerEntity,

    @Id
    @GeneratedValue
    val id: Long = 0

)