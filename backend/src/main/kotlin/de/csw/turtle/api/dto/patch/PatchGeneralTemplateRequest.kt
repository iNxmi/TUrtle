package de.csw.turtle.api.dto.patch

import de.csw.turtle.api.entity.GeneralTemplateEntity

class PatchGeneralTemplateRequest(
    val name: String? = null,
    val description: String? = null,
    val content: String? = null,
    val type: GeneralTemplateEntity.Type? = null
) : PatchRequest