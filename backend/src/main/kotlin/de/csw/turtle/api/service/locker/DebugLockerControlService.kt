package de.csw.turtle.api.service.locker

class DebugLockerControlService : LockerControlService {

    override fun trigger(locker: Locker) = "DEBUG: DebugLockerControlService.trigger(${locker.id})"

}