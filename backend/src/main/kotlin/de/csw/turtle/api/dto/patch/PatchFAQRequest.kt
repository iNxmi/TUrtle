package de.csw.turtle.api.dto.patch

class PatchFAQRequest(
    val name: String? = null,
    val title: String? = null,
    val content: String? = null
) : PatchRequest