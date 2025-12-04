package de.csw.turtle.api.service.locker

import de.csw.turtle.TUrtleProperties
import de.csw.turtle.api.entity.LockerEntity
import net.schmizz.sshj.SSHClient
import net.schmizz.sshj.transport.verification.PromiscuousVerifier

class SSHLockerControlService(
    private val properties: TUrtleProperties
) : LockerControlService {

    override fun trigger(locker: LockerEntity): String {
        val input = "~/cabinet${locker.index}Open.sh"

        return SSHClient().use { client ->
            client.addHostKeyVerifier(PromiscuousVerifier())
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