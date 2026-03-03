package de.csw.turtle.api.dto

data class GetConnectionResponse(
    val trusted: Boolean,
    val local: Boolean
)