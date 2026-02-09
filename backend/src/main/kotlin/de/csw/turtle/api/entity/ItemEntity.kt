package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "items")
class ItemEntity(

    @Id @GeneratedValue
    override val id: Long = 0,

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

    var needsConfirmation: Boolean,

    var acquiredAt: Instant,

    @OneToMany(mappedBy = "item")
    val bookings: MutableSet<ItemBookingEntity> = mutableSetOf(),

    //Instant.MIN will be replaced by createdAt in prePersist()
    override var updatedAt: Instant = Instant.MIN,

    @Column(updatable = false)
    override val createdAt: Instant = Instant.now()

) : CRUDEntity {

    @PrePersist
    fun prePersist() {
        updatedAt = createdAt
    }

    @PreUpdate
    fun preUpdate() {
        updatedAt = Instant.now()
    }

    override fun snapshot() = ItemEntity(
        id = id,
        name = name,
        description = description,
        category = category,
        locker = locker,
        needsConfirmation = needsConfirmation,
        acquiredAt = acquiredAt,
        bookings = bookings.toMutableSet(),
        updatedAt = updatedAt,
        createdAt = createdAt
    )

}