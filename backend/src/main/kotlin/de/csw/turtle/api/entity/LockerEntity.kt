package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "lockers")
data class LockerEntity(

    @Column( unique = true)
    var index: Int,

    var name: String,

    @OneToMany(mappedBy = "locker")
    val devices: MutableSet<DeviceEntity> = mutableSetOf()

) : CRUDEntity()