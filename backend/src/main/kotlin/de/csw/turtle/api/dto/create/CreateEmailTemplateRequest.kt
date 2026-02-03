package de.csw.turtle.api.dto.create

data class CreateEmailTemplateRequest(
    val name: String,
    val description: String,
    val subject: String,
    val content: String
) : CreateRequest