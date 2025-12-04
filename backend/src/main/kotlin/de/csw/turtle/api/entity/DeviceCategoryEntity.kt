package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "device_categories")
class DeviceCategoryEntity(

    @Column(unique = true)
    var name: String,

    @OneToMany(mappedBy = "category")
    val devices: MutableSet<DeviceEntity> = mutableSetOf()

) : CRUDEntity()