package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "exceptions")
class ExceptionEntity(

    @Column(nullable = false)
    val endpoint: String,

    @Column(nullable = false)
    val exception: String?,

    @Column(nullable = false, columnDefinition = "TEXT")
    val message: String?,

    @Column(nullable = false, columnDefinition = "TEXT")
    val stackTrace: String,

    @Id
    @GeneratedValue
    val id: Long = 0,

    @Column(nullable = false, updatable = false)
    val createdAt: Instant = Instant.now()

)