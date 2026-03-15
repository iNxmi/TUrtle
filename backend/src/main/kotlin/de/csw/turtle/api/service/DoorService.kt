package de.csw.turtle.api.service

import de.csw.turtle.api.entity.ConfigurationEntity.Key
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.thymeleaf.context.Context
import java.time.Duration

@Service
class DoorService(
    private val configurationService: ConfigurationService,
    private val sshService: SSHService,
    private val thymeleafService: ThymeleafService
) {

    @Transactional
    fun unlock(duration: Duration) {
        val isDebug = configurationService.getTyped<Boolean>(Key.DOOR_DEBUG_ENABLED)
        if (isDebug)
            return

        val context = Context().apply { setVariable("duration", duration) }
        val template = configurationService.getTyped<String>(Key.DOOR_SSH_COMMAND)
        val command = thymeleafService.getRendered(template, context)

        val hostname = configurationService.getTyped<String>(Key.DOOR_SSH_HOSTNAME)
        val port = configurationService.getTyped<Int>(Key.DOOR_SSH_PORT)
        val username = configurationService.getTyped<String>(Key.DOOR_SSH_USERNAME)
        val password = configurationService.getTyped<String>(Key.DOOR_SSH_PASSWORD)

        ssh(hostname, port, username, password, command)
    }

    @Async
    fun ssh(hostname: String, port: Int, username: String, password: String, command: String) =
        sshService.execute(hostname, port, username, password, command)

}