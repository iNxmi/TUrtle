package de.csw.turtle.api.event

import de.csw.turtle.api.entity.UserEntity

data class CreatedUserEvent(
    val entity: UserEntity
)