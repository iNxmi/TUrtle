package de.csw.turtle.api.service.door

import java.time.Duration

class DebugDoorControlService : DoorControlService {

    override fun trigger(duration: Duration) = "DEBUG: DebugDoorControlService.trigger($duration)"

}