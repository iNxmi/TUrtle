package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Instant

@MappedSuperclass
abstract class CRUDEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    //Instant.MIN will be replaced by createdAt in prePersist()
    var updatedAt: Instant = Instant.MIN

    @Column(updatable = false)
    val createdAt: Instant = Instant.now()

    @PrePersist
    fun prePersist() {
        updatedAt = createdAt
    }

    @PreUpdate
    fun preUpdate() {
        updatedAt = Instant.now()
    }

}