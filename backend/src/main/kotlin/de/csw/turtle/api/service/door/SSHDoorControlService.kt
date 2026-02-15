package de.csw.turtle.api.service.door

import de.csw.turtle.api.entity.ConfigurationEntity.Key
import de.csw.turtle.api.service.SSHService
import de.csw.turtle.api.service.ConfigurationService
import de.csw.turtle.api.service.ThymeleafService
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import java.time.Duration

@Service
class SSHDoorControlService(
    private val configurationService: ConfigurationService,
    private val sshService: SSHService,
    private val thymeleafService: ThymeleafService
) : DoorControlService {

    override fun trigger(duration: Duration): String {
        val context = Context().apply { setVariable("duration", duration) }
        val template = configurationService.getTyped<String>(Key.DOOR_SSH_COMMAND)
        val command = thymeleafService.getRendered(template, context)

        val hostname = configurationService.getTyped<String>(Key.DOOR_SSH_HOSTNAME)
        val port = configurationService.getTyped<Int>(Key.DOOR_SSH_PORT)
        val username = configurationService.getTyped<String>(Key.DOOR_SSH_USERNAME)
        val password = configurationService.getTyped<String>(Key.DOOR_SSH_PASSWORD)

        return sshService.execute(hostname, port, username, password, command)
    }

}