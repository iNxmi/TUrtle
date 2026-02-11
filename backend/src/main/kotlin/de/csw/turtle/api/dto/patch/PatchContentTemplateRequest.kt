package de.csw.turtle.api.dto.patch

import de.csw.turtle.api.entity.ContentTemplateEntity

class PatchContentTemplateRequest(
    val name: String? = null,
    val description: String? = null,
    val content: String? = null,
    val type: ContentTemplateEntity.Type? = null
) : PatchRequest