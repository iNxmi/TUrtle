package de.csw.turtle.api.dto.get

import de.csw.turtle.api.entity.SystemSettingEntity
import java.time.Instant

class GetSystemSettingResponse(
    override val id: Long?,

    val key: String?,
    val type: SystemSettingEntity.Type?,
    val value: String?,

    override val updatedAt: Instant?,
    override val createdAt: Instant?
) : GetResponse