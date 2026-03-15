package de.csw.turtle.api.dto.patch

import de.csw.turtle.api.entity.StatisticQueryEntity

class PatchStatisticQueryRequest(
    val name: String? = null,
    val description: String? = null,
    val query: String? = null,
    val type: StatisticQueryEntity.Type? = null
) : PatchRequest