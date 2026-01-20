package de.csw.turtle.api.service.locker

import de.csw.turtle.api.entity.LockerEntity
import org.springframework.stereotype.Service

@Service
interface LockerControlService {

    fun trigger(locker: LockerEntity, ignoreLocked: Boolean = false): String {
        if (locker.locked && !ignoreLocked)
            return "Door is locked."

        return onTrigger(locker)
    }

    fun onTrigger(locker: LockerEntity): String

}