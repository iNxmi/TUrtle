package de.csw.turtle.api.service.door

import org.springframework.stereotype.Service
import java.time.Duration

@Service
interface DoorControlService {

    fun trigger(duration: Duration): String

}