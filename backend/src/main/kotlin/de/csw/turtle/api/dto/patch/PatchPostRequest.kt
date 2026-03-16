package de.csw.turtle.api.dto.patch

class PatchPostRequest(
    val name: String? = null,
    val description: String? = null,
    val title: String? = null,
    val content: String? = null,
    val enabled: Boolean? = null
) : PatchRequest