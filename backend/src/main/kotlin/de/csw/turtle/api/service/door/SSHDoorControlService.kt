package de.csw.turtle.api.service.door

import de.csw.turtle.TUrtleProperties
import net.schmizz.sshj.SSHClient
import net.schmizz.sshj.transport.verification.PromiscuousVerifier
import java.time.Duration

class SSHDoorControlService(
    private val properties: TUrtleProperties
) : DoorControlService {

    override fun trigger(seconds: Int): String {
        val input = "~/doorOpen.sh $seconds"

        return SSHClient().use { client ->
            client.addHostKeyVerifier(PromiscuousVerifier())
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