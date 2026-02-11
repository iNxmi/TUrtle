package de.csw.turtle.api.dto.create

data class CreateFAQRequest(
    val name: String,
    val title: String,
    val content: String,
    val enabled: Boolean
) : CreateRequest