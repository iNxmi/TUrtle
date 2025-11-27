package de.csw.turtle.api.dto.create

data class CreateLockerRequest(
    val index: Int,
    val name: String
) : CRUDCreateRequest