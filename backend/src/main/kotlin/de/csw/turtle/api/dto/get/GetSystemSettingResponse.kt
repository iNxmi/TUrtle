package de.csw.turtle.api.dto.get

import de.csw.turtle.api.entity.SystemSettingEntity
import java.time.Instant

class GetSystemSettingResponse(
    override val id: Long?,

    val key: String?,
    val type: SystemSettingEntity.Type?,
    val value: String?,
    val visibility: SystemSettingEntity.Visibility?,

    override val updatedAt: Instant?,
    override val createdAt: Instant?
) : GetResponse {

    constructor(entity: SystemSettingEntity) : this(
        id = entity.id,

        key = entity.key,
        type = entity.type,
        value = entity.value,
        visibility = entity.visibility,

        updatedAt = entity.updatedAt,
        createdAt = entity.createdAt
    )

}