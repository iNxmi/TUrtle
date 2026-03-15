package de.csw.turtle.api.dto.create

import de.csw.turtle.api.entity.StatisticQueryEntity

data class CreateStatisticQueryRequest(
    val name: String,
    val description: String,
    val query: String,
    val type: StatisticQueryEntity.Type
) : CreateRequest