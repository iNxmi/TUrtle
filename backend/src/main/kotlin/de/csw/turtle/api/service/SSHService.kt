package de.csw.turtle.api.service

import de.csw.turtle.api.exception.HttpException
import net.schmizz.sshj.SSHClient
import net.schmizz.sshj.transport.TransportException
import net.schmizz.sshj.transport.verification.PromiscuousVerifier
import org.springframework.stereotype.Service
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.time.Duration

@Service
class SSHService {

    fun execute(
        hostname: String,
        port: Int,
        username: String,
        password: String,
        input: String,
        timeout: Duration = Duration.ofSeconds(5)
    ): String = try {
        SSHClient().use { client ->
            client.connectTimeout = timeout.toMillis().toInt()
            client.addHostKeyVerifier(PromiscuousVerifier())
            client.connect(hostname, port)
            client.authPassword(username, password)

            client.startSession().use { session ->
                session.exec(input).use { command ->
                    command.join()
                    val bytes = command.inputStream.readAllBytes()
                    String(bytes)
                }
            }
        }
    } catch (exception: SocketTimeoutException) {
        throw HttpException.GatewayTimeout(exception.message)
    } catch (exception: ConnectException) {
        throw HttpException.BadGateway(exception.message)
    } catch (exception: TransportException) {
        throw HttpException.BadGateway(exception.message)
    }

}