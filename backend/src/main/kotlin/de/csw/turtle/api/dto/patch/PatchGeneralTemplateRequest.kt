package de.csw.turtle.api.dto.patch

class PatchGeneralTemplateRequest(
    val name: String? = null,
    val description: String? = null,
    val content: String? = null
) : PatchRequest