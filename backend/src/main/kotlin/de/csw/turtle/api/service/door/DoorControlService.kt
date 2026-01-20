package de.csw.turtle.api.service.door

import org.springframework.stereotype.Service

@Service
interface DoorControlService {

    fun trigger(seconds: Int): String = onTrigger(seconds)
    fun onTrigger(seconds: Int): String

}