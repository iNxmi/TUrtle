package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "support_tickets")
class SupportTicketEntity(

    @Id @GeneratedValue
    override val id: Long = 0,

    @Enumerated(EnumType.STRING)
    var urgency: Urgency,

    @Enumerated(EnumType.STRING)
    var category: Category,

    var email: String,

    var subject: String,

    @Column(columnDefinition = "TEXT")
    var description: String,

    @Enumerated(EnumType.STRING)
    var status: Status,

    //Instant.MIN will be replaced by createdAt in prePersist()
    override var updatedAt: Instant = Instant.MIN,

    @Column(updatable = false)
    override val createdAt: Instant = Instant.now()

) : CRUDEntity {

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

    @PrePersist
    fun prePersist() {
        updatedAt = createdAt
    }

    @PreUpdate
    fun preUpdate() {
        updatedAt = Instant.now()
    }

    override fun snapshot() = SupportTicketEntity(
        id = id,
        urgency = urgency,
        category = category,
        email = email,
        subject = subject,
        description = description,
        status = status,
        updatedAt = updatedAt,
        createdAt = createdAt
    )

}