package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "exceptions")
class ExceptionEntity(

    var endpoint: String,

    var exception: String?,

    @Column(columnDefinition = "TEXT")
    var message: String?,

    @Column(columnDefinition = "TEXT")
    var stackTrace: String

) : CRUDEntity()