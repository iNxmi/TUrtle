package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "support_ticket_categories")
class SupportTicketCategoryEntity(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long = 0,

    @Column(unique = true)
    var name: String,

    @OneToMany(mappedBy = "category")
    val supportTickets: MutableSet<SupportTicketEntity> = mutableSetOf(),

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

    override fun snapshot() = SupportTicketCategoryEntity(
        id = id,
        name = name,
        supportTickets = supportTickets.toMutableSet(),
        updatedAt = updatedAt,
        createdAt = createdAt
    )

}