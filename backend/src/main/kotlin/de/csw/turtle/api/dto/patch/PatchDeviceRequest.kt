package de.csw.turtle.api.dto.patch

data class PatchDeviceRequest(
    val name: String? = null,
    val description: String? = null,
    val inventoryId: String? = null,
    val categoryId: Long? = null,
    val lockerId: Long? = null
) : CRUDPatchRequest