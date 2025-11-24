package de.csw.turtle.api.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.OneToMany

@Entity
data class LockerEntity(
    @Column(nullable = false, unique = true)
    val index: Int,

    @Column(nullable = false)
    val name: String,

    @OneToMany(mappedBy = "locker")
    val devices: MutableSet<DeviceEntity> = mutableSetOf(),

    @Id
    @GeneratedValue
    val id: Long = 0
)