package de.csw.turtle.api.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

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