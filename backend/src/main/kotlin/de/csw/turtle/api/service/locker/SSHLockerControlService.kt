package de.csw.turtle.api.service.locker

import de.csw.turtle.api.entity.LockerEntity
import de.csw.turtle.api.service.MustacheService
import de.csw.turtle.api.service.SSHService
import de.csw.turtle.api.service.SystemSettingService
import org.springframework.stereotype.Service

@Service
class SSHLockerControlService(
    private val systemSettingService: SystemSettingService,
    private val sshService: SSHService,
    private val mustacheService: MustacheService
) : LockerControlService(systemSettingService) {

    override fun onTrigger(locker: LockerEntity): String {
        val variables: Map<String, Any?> = mapOf("index" to locker.index)
        val template = systemSettingService.getTyped<String>("locker.ssh.command")
        val command = mustacheService.getInserted(template, variables)

        val hostname = systemSettingService.getTyped<String>("locker.ssh.hostname")
        val port = systemSettingService.getTyped<Int>("locker.ssh.port")
        val username = systemSettingService.getTyped<String>("locker.ssh.username")
        val password = systemSettingService.getTyped<String>("locker.ssh.password")

        return sshService.execute(hostname, port, username, password, command)
    }

}