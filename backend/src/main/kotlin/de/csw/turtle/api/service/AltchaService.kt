package de.csw.turtle.api.service

import de.csw.turtle.api.entity.ConfigurationEntity.Key
import de.csw.turtle.api.exception.HttpException
import jakarta.transaction.Transactional
import org.altcha.altcha.Altcha
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class AltchaService(
    private val configurationService: ConfigurationService
) {

    private fun getSecret() = configurationService.getTyped<String>(Key.ALTCHA_SECRET)

    @Transactional
    fun create(): Altcha.Challenge {
        val secret = getSecret()
        val maxNumber = configurationService.getTyped<Long>(Key.ALTCHA_MAX_NUMBER)
        val duration = configurationService.getTyped<Duration>(Key.ALTCHA_DURATION)

        val options = Altcha.ChallengeOptions()
            .setHmacKey(secret)
            .setMaxNumber(maxNumber)
            .setExpiresInSeconds(duration.toSeconds())
            .setSecureRandomNumber(true)

        return Altcha.createChallenge(options)
    }

    @Transactional
    fun isTrusted(ipAddress: String): Boolean {
        val trustedIps = configurationService.getTyped<List<String>>(Key.ALTCHA_TRUSTED_IPS)
        return trustedIps.contains(ipAddress)
    }

    fun isValid(token: String): Boolean = try {
        val secret = getSecret()
        Altcha.verifySolution(token, secret, true)
    } catch (exception: Exception) {
        throw HttpException.BadRequest("Invalid token. ${exception.message}")
    }

}