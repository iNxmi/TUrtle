package de.csw.turtle.api.dto.create

data class CreateDeviceRequest(
    val name: String,
    val description: String,
    val inventoryId: String,
    val categoryId: Long,
    val lockerId: Long
) : CRUDCreateRequest