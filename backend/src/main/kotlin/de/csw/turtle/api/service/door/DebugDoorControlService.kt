package de.csw.turtle.api.service.door

import de.csw.turtle.api.service.SystemSettingService

class DebugDoorControlService(
    systemSettingService: SystemSettingService
) : DoorControlService(systemSettingService) {

    override fun onTrigger(seconds: Int) = "DEBUG: DebugDoorControlService.trigger($seconds)"

}