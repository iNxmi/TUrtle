package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "statistic_queries")
class StatisticQueryEntity(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long = 0,

    var name: String,

    @Column(columnDefinition = "TEXT")
    var description: String,

    @Column(columnDefinition = "TEXT")
    var query: String,

    var type: Type,

    override var updatedAt: Instant = Instant.MIN,

    @Column(updatable = false)
    override val createdAt: Instant = Instant.now()

) : CRUDEntity {

    enum class Type {
        MAP, LIST, SINGLE_VALUE;
    }

    override fun snapshot() = StatisticQueryEntity(
        id = id,
        name = name,
        description = description,
        query = query,
        type = type,
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