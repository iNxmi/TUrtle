package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "support_tickets")
data class SupportTicketEntity(

    @Enumerated(EnumType.STRING)
    var urgency: Urgency,

    @Enumerated(EnumType.STRING)
    var category: Category,

    var email: String,

    var subject: String,

    @Column(columnDefinition = "TEXT")
    var description: String,

    @Enumerated(EnumType.STRING)
    var status: Status = Status.OPEN

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