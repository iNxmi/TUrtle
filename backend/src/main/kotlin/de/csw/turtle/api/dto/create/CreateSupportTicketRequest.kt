package de.csw.turtle.api.dto.create

data class CreateSupportTicketRequest(
    val urgencyId: Long,
    val categoryId: Long,
    val email: String,
    val subject: String,
    val content: String,
    val altchaToken: String? = null
) : CreateRequest