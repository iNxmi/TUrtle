package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "support_tickets")
data class SupportTicketEntity(
    @Column(nullable = false)
    var urgency: Urgency,

    @Column(nullable = false)
    var category: Category,

    @Column(nullable = false)
    var email: String,

    @Column(nullable = false)
    var subject: String,

    @Column(nullable = false)
    var description: String,

    @Id
    @GeneratedValue
    val id: Long = 0,

    @Column(nullable = false, updatable = false)
    val createdAt: Instant = Instant.now()
) {

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

}