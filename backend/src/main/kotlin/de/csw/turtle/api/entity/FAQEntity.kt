package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "faq")
class FAQEntity(

    @Id @GeneratedValue
    override val id: Long = 0,

    @Column(unique = true)
    var name: String,

    var title: String,

    @Column(columnDefinition = "TEXT")
    var content: String,

    var enabled: Boolean,

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

    override fun snapshot() = FAQEntity(
        id = id,
        name = name,
        title = title,
        content = content,
        enabled = enabled,
        updatedAt = updatedAt,
        createdAt = createdAt
    )

}

