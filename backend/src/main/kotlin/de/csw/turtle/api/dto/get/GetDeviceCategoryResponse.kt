package de.csw.turtle.api.dto.get

import de.csw.turtle.api.entity.DeviceCategoryEntity

data class GetDeviceCategoryResponse(
    val id: Long,
    val name: String
) {

    constructor(category: DeviceCategoryEntity) : this(
        id = category.id,
        name = category.name
    )

}