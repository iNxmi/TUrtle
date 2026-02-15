package de.csw.turtle.api.dto.patch

import de.csw.turtle.api.entity.ConfigurationEntity.*

class PatchConfigurationRequest(
    val key: Key? = null,
    val type: Type? = null,
    val value: String? = null,
    val visibility: Visibility? = null,
) : PatchRequest