package de.csw.turtle.api.event

import de.csw.turtle.api.entity.RoomBookingEntity

data class PatchedRoomBookingEvent(
    val pre: RoomBookingEntity,
    val post: RoomBookingEntity
)