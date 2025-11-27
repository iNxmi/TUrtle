package de.csw.turtle.api.entity

import jakarta.persistence.MappedSuperclass
import java.time.Instant

@MappedSuperclass
abstract class CRUDEntity {
    abstract val id: Long
    abstract val createdAt: Instant
}