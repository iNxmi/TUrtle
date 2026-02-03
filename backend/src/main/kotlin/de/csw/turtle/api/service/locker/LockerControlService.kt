package de.csw.turtle.api.service.locker

import de.csw.turtle.api.entity.LockerEntity

interface LockerControlService {
    fun trigger(locker: LockerEntity): String
}