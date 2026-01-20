package de.csw.turtle.api.service.door

class DebugDoorControlService : DoorControlService {

    override fun onTrigger(seconds: Int) = "DEBUG: DebugDoorControlService.trigger($seconds)"

}