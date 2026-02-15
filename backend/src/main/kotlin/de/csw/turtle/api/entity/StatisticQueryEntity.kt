package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
class StatisticQueryEntity(

    @Id @GeneratedValue
    override val id: Long = 0,

    var name: String,

    @Column(columnDefinition = "TEXT")
    var description: String,

    @Column(columnDefinition = "TEXT")
    var query: String,

    override var updatedAt: Instant = Instant.MIN,

    @Column(updatable = false)
    override val createdAt: Instant = Instant.now()

) : CRUDEntity {

    override fun snapshot() = StatisticQueryEntity(
        id = id,
        name = name,
        description = description,
        query = query,
        updatedAt = updatedAt,
        createdAt = createdAt
    )

    @PrePersist
    fun prePersist() {
        updatedAt = createdAt
    }

    @PreUpdate
    fun preUpdate() {
        updatedAt = Instant.now()
    }

}