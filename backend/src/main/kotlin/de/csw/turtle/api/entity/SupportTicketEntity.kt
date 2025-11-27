package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "support_tickets")
data class SupportTicketEntity(
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var urgency: Urgency,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var category: Category,

    @Column(nullable = false)
    var email: String,

    @Column(nullable = false)
    var subject: String,

    @Column(nullable = false, columnDefinition = "TEXT")
    var description: String,

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    var status: Status = Status.OPEN,

    @Id
    @GeneratedValue
    override val id: Long = 0,

    @Column(nullable = false, updatable = false)
    override val createdAt: Instant = Instant.now()
) : CRUDEntity() {

    enum class Urgency {
        LOW,
        MEDIUM,
        HIGH,
        CRITICAL
    }

    enum class Category {
        TECHNICAL,
        BILLING,
        GENERAL,
        OTHER
    }

    enum class Status {
        OPEN,
        IN_PROGRESS,
        ON_HOLD,
        CLOSED
    }

}