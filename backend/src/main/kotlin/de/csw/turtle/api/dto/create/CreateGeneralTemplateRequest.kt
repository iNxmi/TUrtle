package de.csw.turtle.api.dto.create

import de.csw.turtle.api.entity.GeneralTemplateEntity

data class CreateGeneralTemplateRequest(
    val name: String,
    val description: String,
    val content: String,
    val type: GeneralTemplateEntity.Type? = null
) : CreateRequest