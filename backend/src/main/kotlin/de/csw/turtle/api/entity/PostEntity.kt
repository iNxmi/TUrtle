package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Instant


@Entity
@Table(name = "posts")
class PostEntity(

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    override val id: Long = 0,

    var name: String,

    var description: String,

    var title: String,

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

    override fun snapshot() = PostEntity(
        id = id,
        name = name,
        description = description,
        title = title,
        content = content,
        enabled = enabled,
        updatedAt = updatedAt,
        createdAt = createdAt
    )

}