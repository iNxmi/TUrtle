package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "items")
class ItemEntity(

    @Column(unique = true)
    var name: String,

    @Column(columnDefinition = "TEXT")
    var description: String,

    @ManyToOne
    @JoinColumn(name = "category_id")
    var category: ItemCategoryEntity,

    @ManyToOne
    @JoinColumn(name = "locker_id")
    var locker: LockerEntity,

    var acquiredAt: Instant,

    @OneToMany(mappedBy = "item")
    val bookings: MutableSet<ItemBookingEntity> = mutableSetOf()

) : CRUDEntity()