package de.csw.turtle.api.dto.patch

import de.csw.turtle.api.entity.SystemSettingEntity

class PatchSystemSettingRequest(
    val key: String? = null,
    val type: SystemSettingEntity.Type? = null,
    val value: String? = null
) : PatchRequest