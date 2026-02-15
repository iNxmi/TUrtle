package de.csw.turtle.api.service.locker

import de.csw.turtle.api.entity.LockerEntity
import de.csw.turtle.api.entity.ConfigurationEntity.Key
import de.csw.turtle.api.service.SSHService
import de.csw.turtle.api.service.ConfigurationService
import de.csw.turtle.api.service.ThymeleafService
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context

@Service
class SSHLockerControlService(
    private val configurationService: ConfigurationService,
    private val sshService: SSHService,
    private val thymeleafService: ThymeleafService
) : LockerControlService {

    override fun trigger(locker: LockerEntity): String {
        val context = Context().apply { setVariable("index", locker.index) }
        val template = configurationService.getTyped<String>(Key.LOCKER_SSH_COMMAND)
        val command = thymeleafService.getRendered(template, context)

        val hostname = configurationService.getTyped<String>(Key.LOCKER_SSH_HOSTNAME)
        val port = configurationService.getTyped<Int>(Key.LOCKER_SSH_PORT)
        val username = configurationService.getTyped<String>(Key.LOCKER_SSH_USERNAME)
        val password = configurationService.getTyped<String>(Key.LOCKER_SSH_PASSWORD)

        return sshService.execute(hostname, port, username, password, command)
    }

}