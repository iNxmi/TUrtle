package de.csw.turtle.api.dto.get

import java.time.Instant

interface GetResponse {
    val id: Long?
    val updatedAt: Instant?
    val createdAt: Instant?
}