package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "templates")
data class TemplateEntity(
    @Column(nullable = false, unique = true)
    var name: String,

    @Column(nullable = false)
    var description: String,

    @Column(nullable = false)
    var content: String,

    @Id
    @GeneratedValue
    override val id: Long = 0,

    @Column(nullable = false)
    override val createdAt: Instant = Instant.now()
) : CRUDEntity()