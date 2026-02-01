package de.csw.turtle.api.dto.patch

class PatchEmailTemplateRequest(
    val name: String? = null,
    val description: String? = null,
    val subject: String? = null,
    val content: String? = null
) : PatchRequest