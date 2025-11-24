package de.csw.turtle.api.service.locker

import de.csw.turtle.TUrtleProperties
import net.schmizz.sshj.SSHClient

class SSHLockerControlService(
    private val properties: TUrtleProperties
) : LockerControlService {

    override fun trigger(locker: Locker): String {
        val input = "~/cabinet${locker.id}Open.sh"

        return SSHClient().use { client ->
            client.connect(properties.ssh.locker.host, properties.ssh.locker.port)
            client.authPassword(properties.ssh.locker.username, properties.ssh.locker.password)

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