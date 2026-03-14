package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Duration
import java.time.Instant
import java.util.*

@Entity
@Table(name = "verification_sessions")
class VerificationSessionEntity(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long = 0,

    @Column(unique = true)
    val uuid: UUID = UUID.randomUUID(),

    @ManyToOne
    val user: UserEntity,

    @Enumerated(EnumType.STRING)
    val type: Type,

    var codeHash: String,

    var attempts: Int = 0,

    var lastSentAt: Instant = Instant.MIN,

    var expiresAt: Instant,

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
        lastSentAt = createdAt
    }

    @PreUpdate
    fun preUpdate() {
        updatedAt = Instant.now()
    }

    override fun snapshot() = VerificationSessionEntity(
        id = id,
        uuid = uuid,
        user = user,
        codeHash = codeHash,
        attempts = attempts,
        type = type,
        expiresAt = expiresAt,
        updatedAt = updatedAt,
        createdAt = createdAt
    )

    fun isExpired(now: Instant = Instant.now()): Boolean = now.isAfter(expiresAt)

    fun canResend(cooldown: Duration, now: Instant = Instant.now()): Boolean =
        now.isAfter(lastSentAt.plus(cooldown))

}