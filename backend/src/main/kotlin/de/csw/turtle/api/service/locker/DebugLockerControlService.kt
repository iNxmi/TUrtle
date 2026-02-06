package de.csw.turtle.api.service.locker

import de.csw.turtle.api.entity.LockerEntity
import org.springframework.stereotype.Service

@Service
class DebugLockerControlService : LockerControlService {
    override fun trigger(locker: LockerEntity) = "DEBUG: DebugLockerControlService.trigger(${locker.index})"
}