package de.csw.turtle.api.dto.create

data class CreateStatisticQueryRequest(
    val name: String,
    val description: String,
    val query: String
) : CreateRequest