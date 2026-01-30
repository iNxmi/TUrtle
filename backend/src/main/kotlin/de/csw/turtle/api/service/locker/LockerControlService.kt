package de.csw.turtle.api.service.locker

import de.csw.turtle.api.entity.LockerEntity
import de.csw.turtle.api.exception.HttpException
import org.springframework.stereotype.Service

@Service
interface LockerControlService {

    fun trigger(locker: LockerEntity, ignoreLocked: Boolean = false): String {
        if (!locker.isSoftwareUnlockable)
            throw HttpException.Forbidden("Cannot be opened by software.")

        if (locker.locked && !ignoreLocked)
            throw HttpException.ServiceUnavailable("Locked by administrator.")

        return onTrigger(locker)
    }

    fun onTrigger(locker: LockerEntity): String

}