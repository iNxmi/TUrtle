package de.csw.turtle.api.dto.create

data class CreateTemplateRequest(
    val name: String,
    val description: String,
    val content: String
) : CRUDCreateRequest