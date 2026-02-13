package de.csw.turtle.api.dto.create

import de.csw.turtle.api.entity.ContentTemplateEntity.Type

data class CreateContentTemplateRequest(
    val name: String,
    val description: String,
    val content: String,
    val type: Type? = null
) : CreateRequest