package de.csw.turtle.api.dto.patch

class PatchSystemSettingRequest(
    val key: String? = null,
    val value: String? = null
) : PatchRequest