package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Instant

@MappedSuperclass
abstract class CRUDEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    var updatedAt: Instant = Instant.now()

    @Column(updatable = false)
    val createdAt: Instant = Instant.now()

    @PreUpdate
    fun preUpdate() {
        updatedAt = Instant.now()
    }

}