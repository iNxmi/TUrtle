package de.csw.turtle.api.service.locker

import de.csw.turtle.api.entity.LockerEntity
import de.csw.turtle.api.exception.HttpException
import de.csw.turtle.api.service.SystemSettingService
import org.springframework.stereotype.Service
import java.time.LocalTime

@Service
abstract class LockerControlService(
    private val systemSettingService: SystemSettingService
) {

    fun trigger(locker: LockerEntity, ignoreLocked: Boolean = false): String {
        if (!locker.isSoftwareUnlockable)
            throw HttpException.Forbidden("Cannot be opened by software.")

        if (locker.locked && !ignoreLocked)
            throw HttpException.ServiceUnavailable("Locked by administrator.")

        val now = LocalTime.now()
        val start = systemSettingService.getTyped<LocalTime>("locker.schedule.start")
        val end = systemSettingService.getTyped<LocalTime>("locker.schedule.end")
        if (now.isBefore(start) || now.isAfter(end))
            throw HttpException.ServiceUnavailable("Outside of schedule. $start to $end.")

        return onTrigger(locker)
    }

    abstract fun onTrigger(locker: LockerEntity): String

}