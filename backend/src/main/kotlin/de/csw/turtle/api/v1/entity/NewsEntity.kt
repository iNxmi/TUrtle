package de.csw.turtle.api.v1.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
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