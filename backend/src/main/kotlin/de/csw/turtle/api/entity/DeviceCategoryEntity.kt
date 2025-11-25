package de.csw.turtle.api.entity

import jakarta.persistence.*

@Entity
class DeviceCategoryEntity(
    @Column(nullable = false)
    var name: String,

    @OneToMany(mappedBy = "category")
    val devices: MutableSet<DeviceEntity> = mutableSetOf(),

    @Id
    @GeneratedValue
    val id: Long = 0,
)