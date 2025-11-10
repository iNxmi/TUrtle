package de.csw.turtle.api.entity

import jakarta.persistence.*
import java.time.Instant

@Entity
@Table(name = "news")
data class NewsEntity(
    @Column(nullable = false)
    val title: String,

    val updatedAt: Instant? = null,

    val publishedAt: Instant? = null,

    @Column(nullable = false)
    val status: Status = Status.DRAFT,

    @Column(nullable = false, updatable = false)
    val createdAt: Instant = Instant.now(),

    @Id
    @GeneratedValue
    @Column(updatable = false)
    val id: Long = 0,
) {

    enum class Status {
        DRAFT, PUBLISHED, SCHEDULED
    }

}