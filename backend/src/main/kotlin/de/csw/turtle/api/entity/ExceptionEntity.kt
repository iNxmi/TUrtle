package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "exceptions")
class ExceptionEntity(

    @Id @GeneratedValue
    override val id: Long = 0,

    var endpoint: String,

    var exception: String?,

    @Column(columnDefinition = "TEXT")
    var message: String?,

    @Column(columnDefinition = "TEXT")
    var stackTrace: String,

    //Instant.MIN will be replaced by createdAt in prePersist()
    override var updatedAt: Instant = Instant.MIN,

    @Column(updatable = false)
    override val createdAt: Instant = Instant.now()

) : CRUDEntity {

    @PrePersist
    fun prePersist() {
        updatedAt = createdAt
    }

    @PreUpdate
    fun preUpdate() {
        updatedAt = Instant.now()
    }

    override fun snapshot() = ExceptionEntity(
        id = id,
        endpoint = endpoint,
        exception = exception,
        message = message,
        stackTrace = stackTrace,
        updatedAt = updatedAt,
        createdAt = createdAt
    )

}