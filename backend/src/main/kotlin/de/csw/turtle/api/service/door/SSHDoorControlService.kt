package de.csw.turtle.api.service.door

import de.csw.turtle.api.service.SystemSettingService
import net.schmizz.sshj.SSHClient
import net.schmizz.sshj.transport.verification.PromiscuousVerifier

class SSHDoorControlService(
    private val systemSettingsService: SystemSettingService
) : DoorControlService {

    override fun onTrigger(seconds: Int): String {
        val input = "~/doorOpen.sh $seconds"

        val host = systemSettingsService.getTyped<String>("door.ssh.host")
        val port = systemSettingsService.getTyped<Int>("door.ssh.port")
        val username = systemSettingsService.getTyped<String>("door.ssh.username")
        val password = systemSettingsService.getTyped<String>("door.ssh.password")

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