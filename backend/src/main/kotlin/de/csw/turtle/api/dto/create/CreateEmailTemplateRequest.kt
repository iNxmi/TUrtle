package de.csw.turtle.api.dto.create

import de.csw.turtle.api.entity.EmailTemplateEntity

data class CreateEmailTemplateRequest(
    val name: String,
    val description: String,
    val subject: String,
    val content: String,
    val type: EmailTemplateEntity.Type? = null
) : CreateRequest