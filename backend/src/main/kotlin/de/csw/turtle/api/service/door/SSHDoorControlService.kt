package de.csw.turtle.api.service.door

import de.csw.turtle.api.service.MustacheService
import de.csw.turtle.api.service.SSHService
import de.csw.turtle.api.service.SystemSettingService
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class SSHDoorControlService(
    private val systemSettingService: SystemSettingService,
    private val sshService: SSHService,
    private val mustacheService: MustacheService
) : DoorControlService {

    override fun trigger(duration: Duration): String {
        val variables: Map<String, Any?> = mapOf("seconds" to duration.toSeconds())
        val template = systemSettingService.getTyped<String>("door.ssh.command")
        val command = mustacheService.getInserted(template, variables)

        val hostname = systemSettingService.getTyped<String>("door.ssh.hostname")
        val port = systemSettingService.getTyped<Int>("door.ssh.port")
        val username = systemSettingService.getTyped<String>("door.ssh.username")
        val password = systemSettingService.getTyped<String>("door.ssh.password")

        return sshService.execute(hostname, port, username, password, command)
    }

}