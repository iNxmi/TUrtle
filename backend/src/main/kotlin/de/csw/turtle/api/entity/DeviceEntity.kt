package de.csw.turtle.api.entity

import jakarta.persistence.*

@Entity
data class DeviceEntity(
    @Id
    val id: String,

    @Column(nullable = false)
    val name: String,

    @Column(nullable = false)
    val description: String,

    @Column(nullable = false)
    val accessories: String,

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    val category: DeviceCategoryEntity,

    @ManyToOne
    @JoinColumn(name = "locker_id", nullable = false)
    val locker: LockerEntity
)