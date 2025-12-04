package de.csw.turtle.api.dto.create

class CreateFAQRequest(
    val name: String,
    val title: String,
    val content: String
) : CRUDCreateRequest