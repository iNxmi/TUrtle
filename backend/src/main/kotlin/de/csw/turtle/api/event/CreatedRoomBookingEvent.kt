package de.csw.turtle.api.event

import de.csw.turtle.api.entity.RoomBookingEntity

data class CreatedRoomBookingEvent(
    val entity: RoomBookingEntity
)