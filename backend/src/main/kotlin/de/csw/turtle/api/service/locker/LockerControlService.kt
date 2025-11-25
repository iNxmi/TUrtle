package de.csw.turtle.api.service.locker

import de.csw.turtle.api.entity.LockerEntity
import org.springframework.stereotype.Service

@Service
interface LockerControlService {

    fun trigger(locker: LockerEntity): String

}