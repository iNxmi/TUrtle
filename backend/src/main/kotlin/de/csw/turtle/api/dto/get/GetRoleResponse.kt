package de.csw.turtle.api.dto.get

import java.time.Instant

data class GetRoleResponse(
    override val id: Long,
    val name: String,
    val permissions: Set<String>,
    override val createdAt: Instant
) : CRUDGetResponse