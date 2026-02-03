package de.csw.turtle.api.service.door

import java.time.Duration

interface DoorControlService {
    fun trigger(duration: Duration): String
}