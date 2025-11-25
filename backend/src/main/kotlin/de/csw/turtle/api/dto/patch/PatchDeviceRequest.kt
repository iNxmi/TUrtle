package de.csw.turtle.api.dto.patch

data class PatchDeviceRequest(
    val name: String?,
    val description: String?,
    val inventoryId: String?,
    val categoryId: Long?,
    val lockerId: Long?
)