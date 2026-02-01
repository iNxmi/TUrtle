package de.csw.turtle.api.service.locker

import de.csw.turtle.api.entity.LockerEntity
import de.csw.turtle.api.service.ThymeleafService
import de.csw.turtle.api.service.SSHService
import de.csw.turtle.api.service.SystemSettingService
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context

@Service
class SSHLockerControlService(
    private val systemSettingService: SystemSettingService,
    private val sshService: SSHService,
    private val thymeleafService: ThymeleafService
) : LockerControlService {

    override fun trigger(locker: LockerEntity): String {
        val context = Context().apply {
            setVariable("index", locker.index)
        }
        val template = systemSettingService.getTyped<String>("locker.ssh.command")
        val command = thymeleafService.getRendered(template, context)

        val hostname = systemSettingService.getTyped<String>("locker.ssh.hostname")
        val port = systemSettingService.getTyped<Int>("locker.ssh.port")
        val username = systemSettingService.getTyped<String>("locker.ssh.username")
        val password = systemSettingService.getTyped<String>("locker.ssh.password")

        return sshService.execute(hostname, port, username, password, command)
    }

}