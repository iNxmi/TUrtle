package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "device_categories")
class DeviceCategoryEntity(
    @Column(nullable = false)
    var name: String,

    @OneToMany(mappedBy = "category")
    val devices: MutableSet<DeviceEntity> = mutableSetOf(),

    @Id
    @GeneratedValue
    override val id: Long = 0,

    @Column(nullable = false)
    override val createdAt: Instant = Instant.now()
) : CRUDEntity()