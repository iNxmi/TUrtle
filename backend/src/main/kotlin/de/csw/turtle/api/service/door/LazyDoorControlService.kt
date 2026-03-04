package de.csw.turtle.api.service.door

import de.csw.turtle.api.entity.ConfigurationEntity.Key
import de.csw.turtle.api.service.ConfigurationService
import de.csw.turtle.api.service.SSHService
import de.csw.turtle.api.service.ThymeleafService
import java.time.Duration

class LazyDoorControlService(
    private val configurationService: ConfigurationService,
    private val sshService: SSHService,
    private val thymeleafService: ThymeleafService
) : DoorControlService {

    private val delegate: DoorControlService by lazy {
        val isDebug = configurationService.getTyped<Boolean>(Key.DOOR_DEBUG_ENABLED)
        if (isDebug)
            DebugDoorControlService()

        SSHDoorControlService(configurationService, sshService, thymeleafService)
    }

    override fun trigger(duration: Duration) = delegate.trigger(duration)

}