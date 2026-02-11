package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "content_templates")
class ContentTemplateEntity(

    @Id @GeneratedValue
    override val id: Long = 0,

    @Column(unique = true)
    var name: String,

    @Column(columnDefinition = "TEXT")
    var description: String,

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
        IMPRINT,
        GENERAL_DATA_PROTECTION_REGULATION,
        TERMS_OF_SERVICE,
        ABOUT,
        CONTACT
    }

    @PrePersist
    fun prePersist() {
        updatedAt = createdAt
    }

    @PreUpdate
    fun preUpdate() {
        updatedAt = Instant.now()
    }

    override fun snapshot() = ContentTemplateEntity(
        id = id,
        name = name,
        description = description,
        content = content,
        type = type,
        updatedAt = updatedAt,
        createdAt = createdAt
    )

}