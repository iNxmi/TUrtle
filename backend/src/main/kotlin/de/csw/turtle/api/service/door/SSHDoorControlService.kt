package de.csw.turtle.api.service.door

import de.csw.turtle.api.service.ThymeleafService
import de.csw.turtle.api.service.SSHService
import de.csw.turtle.api.service.SystemSettingService
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
        val context = Context().apply {
            setVariable("duration", duration)
        }
        val template = systemSettingService.getTyped<String>("door.ssh.command")
        val command = thymeleafService.getRendered(template, context)

        val hostname = systemSettingService.getTyped<String>("door.ssh.hostname")
        val port = systemSettingService.getTyped<Int>("door.ssh.port")
        val username = systemSettingService.getTyped<String>("door.ssh.username")
        val password = systemSettingService.getTyped<String>("door.ssh.password")

        return sshService.execute(hostname, port, username, password, command)
    }

}