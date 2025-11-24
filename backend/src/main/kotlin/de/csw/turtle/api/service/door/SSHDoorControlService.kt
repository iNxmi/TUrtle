package de.csw.turtle.api.service.door

import de.csw.turtle.TUrtleProperties
import net.schmizz.sshj.SSHClient

class SSHDoorControlService(
    private val properties: TUrtleProperties
) : DoorControlService {

    private val duration = 3

    override fun trigger(): String {
        val input = "~/doorOpen.sh $duration"

        return SSHClient().use { client ->
            client.connect(properties.ssh.door.host, properties.ssh.door.port)
            client.authPassword(properties.ssh.door.username, properties.ssh.door.password)

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