package de.csw.turtle.api.dto.patch

import de.csw.turtle.api.entity.EmailTemplateEntity

class PatchEmailTemplateRequest(
    val name: String? = null,
    val description: String? = null,
    val subject: String? = null,
    val content: String? = null,
    val type: EmailTemplateEntity.Type? = null,
) : PatchRequest