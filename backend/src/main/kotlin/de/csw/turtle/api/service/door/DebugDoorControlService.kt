package de.csw.turtle.api.service.door

class DebugDoorControlService : DoorControlService {

    override fun trigger() = "DEBUG: DebugDoorControlService.trigger()"

}