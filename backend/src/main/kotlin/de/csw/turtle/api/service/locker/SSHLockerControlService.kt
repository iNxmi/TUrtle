package de.csw.turtle.api.service.locker

import de.csw.turtle.api.Settings
import de.csw.turtle.api.entity.LockerEntity
import de.csw.turtle.api.service.SSHService
import de.csw.turtle.api.service.SystemSettingService
import de.csw.turtle.api.service.ThymeleafService
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context

@Service
class SSHLockerControlService(
    private val systemSettingService: SystemSettingService,
    private val sshService: SSHService,
    private val thymeleafService: ThymeleafService
) : LockerControlService {

    override fun trigger(locker: LockerEntity): String {
        val context = Context().apply { setVariable("index", locker.index) }
        val template = systemSettingService.getTyped<String>(Settings.LOCKER_SSH_COMMAND)
        val command = thymeleafService.getRendered(template, context)

        val hostname = systemSettingService.getTyped<String>(Settings.LOCKER_SSH_HOSTNAME)
        val port = systemSettingService.getTyped<Int>(Settings.LOCKER_SSH_PORT)
        val username = systemSettingService.getTyped<String>(Settings.LOCKER_SSH_USERNAME)
        val password = systemSettingService.getTyped<String>(Settings.LOCKER_SSH_PASSWORD)

        return sshService.execute(hostname, port, username, password, command)
    }

}