package de.csw.turtle.api.entity

import jakarta.persistence.MappedSuperclass

@MappedSuperclass
abstract class CRUDEntity {
    abstract val id: Long
}