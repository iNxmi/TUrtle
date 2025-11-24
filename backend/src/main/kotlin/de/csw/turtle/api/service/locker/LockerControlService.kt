package de.csw.turtle.api.service.locker

import org.springframework.stereotype.Service

@Service
interface LockerControlService {

    fun trigger(locker: Locker): String

}