package de.csw.turtle.api.service.door

class DebugDoorControlService : DoorControlService {

    override fun trigger(seconds: Int) = "DEBUG: DebugDoorControlService.trigger($seconds)"

}