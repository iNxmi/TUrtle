package de.csw.turtle.api.dto.get

import de.csw.turtle.api.entity.ConfigurationEntity
import de.csw.turtle.api.entity.ConfigurationEntity.*
import java.time.Instant

class GetConfigurationResponse(
    override val id: Long?,

    val key: Key?,
    val type: Type?,
    val value: String?,
    val visibility: Visibility?,

    override val updatedAt: Instant?,
    override val createdAt: Instant?
) : GetResponse {

    constructor(entity: ConfigurationEntity) : this(
        id = entity.id,

        key = entity.key,
        type = entity.type,
        value = entity.value,
        visibility = entity.visibility,

        updatedAt = entity.updatedAt,
        createdAt = entity.createdAt
    )

}