package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "devices")
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
    override val id: Long = 0,

    @Column(nullable = false)
    override val createdAt: Instant = Instant.now()

) : CRUDEntity()