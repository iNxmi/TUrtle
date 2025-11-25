package de.csw.turtle.api.dto.create

import de.csw.turtle.api.entity.LockerEntity

data class CreateLockerRequest(
    val index: Int,
    val name: String
) {

    fun create() = LockerEntity(
        index = index,
        name = name
    )

}