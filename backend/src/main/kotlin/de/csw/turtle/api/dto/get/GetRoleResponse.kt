package de.csw.turtle.api.dto.get

import de.csw.turtle.api.Permission
import java.time.Instant

data class GetRoleResponse(
    override val id: Long?,

    val name: String?,
    val permissions: Set<Permission>?,

    override val createdAt: Instant?
) : GetResponse