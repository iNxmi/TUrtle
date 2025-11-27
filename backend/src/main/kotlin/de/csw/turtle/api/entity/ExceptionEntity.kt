package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "exceptions")
class ExceptionEntity(

    @Column(nullable = false)
    var endpoint: String,

    @Column
    var exception: String?,

    @Column(columnDefinition = "TEXT")
    var message: String?,

    @Column(nullable = false, columnDefinition = "TEXT")
    var stackTrace: String,

    @Id
    @GeneratedValue
    override val id: Long = 0,

    @Column(nullable = false, updatable = false)
    override val createdAt: Instant = Instant.now()

) : CRUDEntity()