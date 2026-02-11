package de.csw.turtle.api.dto.create

import de.csw.turtle.api.entity.ContentTemplateEntity

data class CreateContentTemplateRequest(
    val name: String,
    val description: String,
    val content: String,
    val type: ContentTemplateEntity.Type? = null
) : CreateRequest