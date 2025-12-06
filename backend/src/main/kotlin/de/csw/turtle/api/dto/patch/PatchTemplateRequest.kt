package de.csw.turtle.api.dto.patch

class PatchTemplateRequest(
    val name: String? = null,
    val description: String? = null,
    val markdown: String? = null
) : CRUDPatchRequest