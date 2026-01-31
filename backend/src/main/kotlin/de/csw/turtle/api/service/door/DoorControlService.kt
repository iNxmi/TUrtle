package de.csw.turtle.api.service.door

import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.service.SystemSettingService
import java.time.LocalTime

abstract class DoorControlService(
    private val systemSettingService: SystemSettingService
) {

    fun trigger(seconds: Int): String {
        val now = LocalTime.now()
        val start = systemSettingService.getTyped<LocalTime>("door.schedule.start")
        val end = systemSettingService.getTyped<LocalTime>("door.schedule.end")
        if (now.isBefore(start) || now.isAfter(end))
            throw HttpException.ServiceUnavailable("Outside of schedule. $start to $end.")

        return onTrigger(seconds)
    }

    abstract fun onTrigger(seconds: Int): String

}