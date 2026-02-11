package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "email_templates")
class EmailTemplateEntity(

    @Id @GeneratedValue
    override val id: Long = 0,

    @Column(unique = true)
    var name: String,

    @Column(columnDefinition = "TEXT")
    var description: String,

    var subject: String,

    @Column(columnDefinition = "TEXT")
    var content: String,

    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    var type: Type?,

    //Instant.MIN will be replaced by createdAt in prePersist()
    override var updatedAt: Instant = Instant.MIN,

    @Column(updatable = false)
    override val createdAt: Instant = Instant.now()

) : CRUDEntity {

    enum class Type {
        USER_CREATED,
        USER_VERIFICATION,
        USER_PASSWORD_RESET,
        ROOM_BOOKING_CREATED,
        ROOM_BOOKING_UPDATED
    }

    @PrePersist
    fun prePersist() {
        updatedAt = createdAt
    }

    @PreUpdate
    fun preUpdate() {
        updatedAt = Instant.now()
    }

    override fun snapshot() = EmailTemplateEntity(
        id = id,
        name = name,
        description = description,
        subject = subject,
        content = content,
        type = type,
        updatedAt = updatedAt,
        createdAt = createdAt
    )

}

