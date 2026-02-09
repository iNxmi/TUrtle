package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "item_categories")
class ItemCategoryEntity(

    @Id @GeneratedValue
    override val id: Long = 0,

    @Column(unique = true)
    var name: String,

    @OneToMany(mappedBy = "category")
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

    override fun snapshot() = ItemCategoryEntity(
        id = id,
        name = name,
        items = items.toMutableSet(),
        updatedAt = updatedAt,
        createdAt = createdAt
    )

}