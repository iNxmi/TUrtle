package de.csw.turtle.api.dto.create

import de.csw.turtle.api.entity.SystemSettingEntity

class CreateSystemSettingRequest(
    val key: String,
    val type: SystemSettingEntity.Type,
    val value: String,
    val visibility: SystemSettingEntity.Visibility
) : CreateRequest