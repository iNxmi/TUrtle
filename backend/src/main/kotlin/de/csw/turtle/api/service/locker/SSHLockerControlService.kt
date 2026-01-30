package de.csw.turtle.api.service.locker

import de.csw.turtle.api.entity.LockerEntity
import de.csw.turtle.api.service.SystemSettingService
import net.schmizz.sshj.SSHClient
import net.schmizz.sshj.transport.verification.PromiscuousVerifier

class SSHLockerControlService(
    private val systemSettingsService: SystemSettingService
) : LockerControlService {

    override fun onTrigger(locker: LockerEntity): String {
        val input = "~/cabinet${locker.index}Open.sh"

        val host = systemSettingsService.getTyped<String>("locker.ssh.host")
        val port = systemSettingsService.getTyped<Int>("locker.ssh.port")
        val username = systemSettingsService.getTyped<String>("locker.ssh.username")
        val password = systemSettingsService.getTyped<String>("locker.ssh.password")

        return SSHClient().use { client ->
            client.addHostKeyVerifier(PromiscuousVerifier())
            client.connect(host, port)
            client.authPassword(username, password)

            client.startSession().use { session ->
                session.exec(input).use { command ->
                    command.join()
                    val bytes = command.inputStream.readAllBytes()
                    String(bytes)
                }
            }
        }
    }

}