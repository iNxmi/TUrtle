package de.csw.turtle.api.dto.patch

class PatchStatisticQueryRequest(
    val name: String? = null,
    val description: String? = null,
    val query: String? = null
) : PatchRequest