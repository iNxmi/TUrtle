package de.csw.turtle.api.service

import de.csw.turtle.api.Settings
import de.csw.turtle.api.exception.HttpException
import org.altcha.altcha.Altcha
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class AltchaService(
    private val systemSettingService: SystemSettingService
) {

    private fun getSecret() = systemSettingService.getTyped<String>(Settings.ALTCHA_SECRET)

    fun create(): Altcha.Challenge {
        val secret = getSecret()
        val maxNumber = systemSettingService.getTyped<Long>(Settings.ALTCHA_MAX_NUMBER)
        val duration = systemSettingService.getTyped<Duration>(Settings.ALTCHA_DURATION)

        val options = Altcha.ChallengeOptions()
            .setHmacKey(secret)
            .setMaxNumber(maxNumber)
            .setExpiresInSeconds(duration.toSeconds())
            .setSecureRandomNumber(true)

        return Altcha.createChallenge(options)
    }

    fun isTrusted(ipAddress: String): Boolean {
        val trustedIps = systemSettingService.getTyped<List<String>>(Settings.ALTCHA_TRUSTED_IPS)
        return trustedIps.contains(ipAddress)
    }

    fun isValid(token: String): Boolean = try {
        val secret = getSecret()
        Altcha.verifySolution(token, secret, true)
    } catch (exception: Exception) {
        throw HttpException.BadRequest("Invalid token. ${exception.message}")
    }

}