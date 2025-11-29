package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "lockers")
data class LockerEntity(
    @Column(nullable = false, unique = true)
    var index: Int,

    @Column(nullable = false)
    var name: String,

    @OneToMany(mappedBy = "locker")
    val devices: MutableSet<DeviceEntity> = mutableSetOf(),

    @Id
    @GeneratedValue
    override val id: Long = 0,

    @Column(nullable = false)
    override val createdAt: Instant = Instant.now()
) : CRUDEntity()