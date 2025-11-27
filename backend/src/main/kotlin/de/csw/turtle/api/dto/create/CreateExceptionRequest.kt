package de.csw.turtle.api.dto.create

data class CreateExceptionRequest(
    val endpoint: String,
    val exception: String,
    val message: String?,
    val stackTrace: String
) : CRUDCreateRequest