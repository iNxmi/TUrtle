package de.csw.turtle.api.event

import de.csw.turtle.api.entity.UserEntity

data class PatchedUserEvent(
    val pre: UserEntity,
    val post: UserEntity
)