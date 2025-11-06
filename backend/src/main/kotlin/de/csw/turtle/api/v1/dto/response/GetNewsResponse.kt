package de.csw.turtle.api.v1.dto.response

import de.csw.turtle.api.v1.entity.NewsEntity
import java.time.Instant

data class GetNewsResponse(
    val id: Long,
    val title: String,
    val createdAt: Instant,
    val updatedAt: Instant?,
    val publishedAt: Instant?,
    val status: NewsEntity.Status
) {
    constructor(news: NewsEntity) : this(
        news.id,
        news.title,
        news.createdAt,
        news.updatedAt,
        news.publishedAt,
        news.status
    )
}