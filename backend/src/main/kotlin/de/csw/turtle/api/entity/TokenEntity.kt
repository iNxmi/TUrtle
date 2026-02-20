package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Duration
import java.time.Instant

@Entity
@Table(name = "tokens")
class TokenEntity(

    @Id @GeneratedValue
    override val id: Long = 0,

    @Column(unique = true)
    val uuid: String,

    val duration: Duration,

    @Enumerated(EnumType.STRING)
    val type: Type,

    //Instant.MIN will be replaced by createdAt in prePersist()
    override var updatedAt: Instant = Instant.MIN,

    @Column(updatable = false)
    override val createdAt: Instant = Instant.now()
) : CRUDEntity {

    enum class Type {
        VERIFICATION,
        PASSWORD_RESET
    }

    @PrePersist
    fun prePersist() {
        updatedAt = createdAt
    }

    @PreUpdate
    fun preUpdate() {
        updatedAt = Instant.now()
    }

    override fun snapshot() = TokenEntity(
        id = id,
        uuid = uuid,
        duration = duration,
        type = type,
        updatedAt = updatedAt,
        createdAt = createdAt
    )

    fun isExpired(): Boolean = Instant.now().isAfter(createdAt.plus(duration))

}