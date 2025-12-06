package de.csw.turtle.api.entity

import jakarta.persistence.*

@Entity
@Table(name = "devices")

data class DeviceEntity(

    var name: String,

    @Column(columnDefinition = "TEXT")
    var description: String,

    var inventoryId: String,

    @ManyToOne
    @JoinColumn(name = "category_id")
    var category: DeviceCategoryEntity,

    @ManyToOne
    @JoinColumn(name = "locker_id")
    var locker: LockerEntity

) : CRUDEntity()