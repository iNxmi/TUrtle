package de.csw.turtle.api.service.locker

import de.csw.turtle.api.entity.ConfigurationEntity.Key
import de.csw.turtle.api.entity.LockerEntity
import de.csw.turtle.api.service.ConfigurationService
import de.csw.turtle.api.service.SSHService
import de.csw.turtle.api.service.ThymeleafService
import java.time.Duration

class LazyLockerControlService(
    private val configurationService: ConfigurationService,
    private val sshService: SSHService,
    private val thymeleafService: ThymeleafService
) : LockerControlService {

    private val delegate: LockerControlService by lazy {
        val isDebug = configurationService.getTyped<Boolean>(Key.DOOR_DEBUG_ENABLED)
        if (isDebug)
            DebugLockerControlService()

        SSHLockerControlService(configurationService, sshService, thymeleafService)
    }

    override fun trigger(locker: LockerEntity) = delegate.trigger(locker)

}