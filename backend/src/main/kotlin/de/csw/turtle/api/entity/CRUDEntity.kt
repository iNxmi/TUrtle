package de.csw.turtle.api.entity

import jakarta.persistence.Column
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import java.time.Instant

@MappedSuperclass
abstract class CRUDEntity {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    @Column(updatable = false)
    val createdAt: Instant = Instant.now()
}