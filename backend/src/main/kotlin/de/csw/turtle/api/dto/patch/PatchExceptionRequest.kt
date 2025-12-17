package de.csw.turtle.api.dto.patch

data class PatchExceptionRequest(
    val endpoint: String? = null,
    val exception: String? = null,
    val message: String? = null,
    val stackTrace: String? = null
) : PatchRequest