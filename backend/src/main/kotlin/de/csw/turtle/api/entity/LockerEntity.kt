package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "lockers")
class LockerEntity(

    @Id @GeneratedValue
    override val id: Long = 0,

    @Column(unique = true)
    var name: String,

    @Column(unique = true)
    var index: Int,

    var isSoftwareUnlockable: Boolean,

    var locked: Boolean,

    @OneToMany(mappedBy = "locker")
    val items: MutableSet<ItemEntity> = mutableSetOf(),

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

    override fun snapshot() = LockerEntity(
        id = id,
        name = name,
        index = index,
        isSoftwareUnlockable = isSoftwareUnlockable,
        locked = locked,
        items = items.toMutableSet(),
        updatedAt = updatedAt,
        createdAt = createdAt
    )

}