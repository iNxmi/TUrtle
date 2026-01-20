package de.csw.turtle.api.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import jakarta.persistence.Table

@Entity
@Table(name = "lockers")
class LockerEntity(

    @Column(unique = true)
    var name: String,

    @Column(unique = true)
    var index: Int,

    var isSoftwareUnlockable: Boolean,

    var locked: Boolean,

    @OneToMany(mappedBy = "locker")
    val devices: MutableSet<DeviceEntity> = mutableSetOf()

) : CRUDEntity()