package de.csw.turtle.api.dto.create

data class CreateGeneralTemplateRequest(
    val name: String,
    val description: String,
    val content: String
) : CreateRequest