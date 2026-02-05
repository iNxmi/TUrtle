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

    fun create(): Altcha.Challenge {
        val secret = systemSettingService.getTyped<String>(Settings.ALTCHA_SECRET)
        val maxNumber = systemSettingService.getTyped<Long>(Settings.ALTCHA_MAX_NUMBER)
        val duration = systemSettingService.getTyped<Duration>(Settings.ALTCHA_DURATION)

        val options = Altcha.ChallengeOptions()
            .setHmacKey(secret)
            .setMaxNumber(maxNumber)
            .setExpiresInSeconds(duration.toSeconds())
            .setSecureRandomNumber(true)

        return Altcha.createChallenge(options)
    }

    fun isValid(token: String): Boolean {
        try {
            val secret = systemSettingService.getTyped<String>(Settings.ALTCHA_SECRET)
            return Altcha.verifySolution(token, secret, true)
        } catch (exception: Exception) {
            throw HttpException.BadRequest("Invalid token. ${exception.message}")
        }
    }

}