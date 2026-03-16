package de.csw.turtle.api.dto.create

data class CreatePostRequest(
    val name: String,
    val description: String,
    val title: String,
    val content: String,
    val enabled: Boolean
) : CreateRequest