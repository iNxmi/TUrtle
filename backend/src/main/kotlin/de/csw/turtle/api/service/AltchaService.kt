package de.csw.turtle.api.service

import de.csw.turtle.api.exception.HttpException
import org.altcha.altcha.Altcha
import org.springframework.stereotype.Service
import java.time.Duration

@Service
class AltchaService(
    private val systemSettingService: SystemSettingService
) {

    fun create(): Altcha.Challenge {
        val secret = systemSettingService.getTyped<String>("altcha.secret")
        val maxNumber = systemSettingService.getTyped<Long>("altcha.max-number")
        val duration = systemSettingService.getTyped<Duration>("altcha.duration")

        val options = Altcha.ChallengeOptions()
            .setHmacKey(secret)
            .setMaxNumber(maxNumber)
            .setExpiresInSeconds(duration.toSeconds())
            .setSecureRandomNumber(true)

        return Altcha.createChallenge(options)
    }

    fun isValid(token: String): Boolean {
        try {
            val secret = systemSettingService.getTyped<String>("altcha.secret")
            return Altcha.verifySolution(token, secret, true)
        } catch (exception: Exception) {
            throw HttpException.BadRequest("Invalid token. ${exception.message}")
        }
    }

}