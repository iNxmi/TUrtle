package de.csw.turtle.api.entity

import java.time.Instant

interface CRUDEntity {

    val id: Long

    var updatedAt: Instant

    val createdAt: Instant

    fun snapshot(): CRUDEntity

}