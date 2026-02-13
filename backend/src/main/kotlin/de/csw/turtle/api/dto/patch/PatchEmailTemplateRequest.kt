package de.csw.turtle.api.dto.patch

import de.csw.turtle.api.entity.EmailTemplateEntity
import de.csw.turtle.api.entity.EmailTemplateEntity.Type

class PatchEmailTemplateRequest(
    val name: String? = null,
    val description: String? = null,
    val subject: String? = null,
    val content: String? = null,
    val type: Type? = null,
) : PatchRequest