package de.csw.turtle.api.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "device_categories")
class DeviceCategoryEntity(

    @Column(unique = true)
    var name: String,

    @OneToMany(mappedBy = "category")
    val devices: MutableSet<DeviceEntity> = mutableSetOf()

) : CRUDEntity()