package de.csw.turtle.api.service.door

import de.csw.turtle.api.Settings
import de.csw.turtle.api.service.SSHService
import de.csw.turtle.api.service.SystemSettingService
import de.csw.turtle.api.service.ThymeleafService
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import java.time.Duration

@Service
class SSHDoorControlService(
    private val systemSettingService: SystemSettingService,
    private val sshService: SSHService,
    private val thymeleafService: ThymeleafService
) : DoorControlService {

    override fun trigger(duration: Duration): String {
        val context = Context().apply { setVariable("duration", duration) }
        val template = systemSettingService.getTyped<String>(Settings.DOOR_SSH_COMMAND)
        val command = thymeleafService.getRendered(template, context)

        val hostname = systemSettingService.getTyped<String>(Settings.DOOR_SSH_HOSTNAME)
        val port = systemSettingService.getTyped<Int>(Settings.DOOR_SSH_PORT)
        val username = systemSettingService.getTyped<String>(Settings.DOOR_SSH_USERNAME)
        val password = systemSettingService.getTyped<String>(Settings.DOOR_SSH_PASSWORD)

        return sshService.execute(hostname, port, username, password, command)
    }

}