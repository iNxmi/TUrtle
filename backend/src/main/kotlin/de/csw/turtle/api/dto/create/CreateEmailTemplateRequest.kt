package de.csw.turtle.api.dto.create

import de.csw.turtle.api.entity.EmailTemplateEntity
import de.csw.turtle.api.entity.EmailTemplateEntity.Type

data class CreateEmailTemplateRequest(
    val name: String,
    val description: String,
    val subject: String,
    val content: String,
    val type: Type? = null
) : CreateRequest