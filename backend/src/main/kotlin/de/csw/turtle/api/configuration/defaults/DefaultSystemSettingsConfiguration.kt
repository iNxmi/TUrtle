package de.csw.turtle.api.configuration.defaults

import com.fasterxml.jackson.databind.ObjectMapper
import de.csw.turtle.api.Settings
import de.csw.turtle.api.entity.SystemSettingEntity.Type
import de.csw.turtle.api.entity.SystemSettingEntity.Visibility
import de.csw.turtle.api.entity.SystemSettingEntity.Visibility.PUBLIC
import de.csw.turtle.api.service.SystemSettingService
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.transaction.annotation.Transactional
import java.security.SecureRandom
import java.time.Duration
import java.time.LocalTime
import java.util.*

@Order(2)
@Configuration
class DefaultSystemSettingsConfiguration(
    private val service: SystemSettingService
) : CommandLineRunner {

    private fun setDefault(
        setting: Settings,
        type: Type,
        visibility: Visibility = Visibility.PRIVATE,
        value: () -> Any
    ) {
        val key = setting.key

        if (service.getByKeyOrNull(key) != null)
            return

        service.create(key, type, value.invoke().toString(), visibility)
    }

    private val secureRandom = SecureRandom()
    private fun randomBase64(size: Int = 64): String {
        val bytes = ByteArray(size)
        secureRandom.nextBytes(bytes)

        val result = Base64.getEncoder()
            .withoutPadding()
            .encodeToString(bytes)

        return result
    }

    @Transactional
    override fun run(vararg args: String) {
        val objectMapper = ObjectMapper()

        setDefault(Settings.GENERAL_FQDN, Type.STRING, PUBLIC) { "csw.tu-darmstadt.de" }

        setDefault(Settings.EMOJIS_ALL, Type.STRING_LIST, PUBLIC) {
            val set = setOf(
                "😈", "😃", "🎩", "👽", "💩", "❤️",
                "💎", "👂", "👍", "🐋", "🐶", "🐸",
                "❄", "🎉", "💿", "🍉", "☎", "🎥",
                "✂", "⚽", "🚀", "💄", "🌂", "🍄",
                "🍀", "🚗", "🍕", "🍔", "🍨", "💣",
                "🐧", "💼", "🌍", "🐝", "🏠", "⏰"
            )
            objectMapper.writeValueAsString(set)
        }
        setDefault(Settings.EMOJIS_SIZE, Type.INT, PUBLIC) { 5 }
        setDefault(Settings.EMOJIS_MAX_RETRIES, Type.INT) { 64 }

        setDefault(Settings.USER_VERIFICATION_DURATION, Type.DURATION) { Duration.ofDays(2) }
        // Regex
        setDefault(Settings.USER_EMAIL_TRUSTED, Type.STRING_LIST, PUBLIC) {
            val set = setOf(".*@stud\\.tu-darmstadt\\.de", ".*@tu-darmstadt\\.de")
            objectMapper.writeValueAsString(set)
        }

        setDefault(Settings.ALTCHA_SECRET, Type.STRING) { randomBase64() }
        setDefault(Settings.ALTCHA_MAX_NUMBER, Type.LONG) { 100_000L }
        setDefault(Settings.ALTCHA_DURATION, Type.DURATION) { Duration.ofMinutes(3) }
        setDefault(Settings.ALTCHA_TRUSTED_IPS, Type.STRING_LIST) {
            val set = setOf("192.168.0.22", "172.18.0.1", "172.18.0.7")
            objectMapper.writeValueAsString(set)
        }

        setDefault(Settings.DOOR_OPEN_DURATION, Type.DURATION, PUBLIC) { Duration.ofSeconds(5) }
        setDefault(Settings.DOOR_SCHEDULE_START, Type.TIME, PUBLIC) { LocalTime.of(6, 0) }
        setDefault(Settings.DOOR_SCHEDULE_END, Type.TIME, PUBLIC) { LocalTime.of(22, 0) }
        //thymeleaf simple template
        setDefault(Settings.DOOR_SSH_COMMAND, Type.STRING) { "~/doorOpen.sh [[\${duration.toSeconds()}]]" }
        setDefault(Settings.DOOR_SSH_HOSTNAME, Type.STRING) { "192.168.0.107" }
        setDefault(Settings.DOOR_SSH_PORT, Type.INT) { 22 }
        setDefault(Settings.DOOR_SSH_USERNAME, Type.STRING) { "" }
        setDefault(Settings.DOOR_SSH_PASSWORD, Type.STRING) { "" }

        setDefault(Settings.LOCKER_SCHEDULE_START, Type.TIME, PUBLIC) { LocalTime.of(6, 0) }
        setDefault(Settings.LOCKER_SCHEDULE_END, Type.TIME, PUBLIC) { LocalTime.of(22, 0) }
        //thymeleaf simple template
        setDefault(Settings.LOCKER_SSH_COMMAND, Type.STRING) { "~/cabinet[[\${index}]]Open.sh" }
        setDefault(Settings.LOCKER_SSH_HOSTNAME, Type.STRING) { "192.168.0.107" }
        setDefault(Settings.LOCKER_SSH_PORT, Type.INT) { 22 }
        setDefault(Settings.LOCKER_SSH_USERNAME, Type.STRING) { "" }
        setDefault(Settings.LOCKER_SSH_PASSWORD, Type.STRING) { "" }

        setDefault(Settings.JWT_SECRET, Type.STRING) { randomBase64() }
        setDefault(Settings.JWT_DURATION_ACCESS, Type.DURATION) { Duration.ofMinutes(5) }
        setDefault(Settings.JWT_DURATION_REFRESH, Type.DURATION) { Duration.ofDays(30) }

        setDefault(Settings.URL_X, Type.STRING, PUBLIC) { "https://twitter.com/CSW_TUDarmstadt" }
        setDefault(Settings.URL_INSTAGRAM, Type.STRING, PUBLIC) { "https://www.instagram.com/csw_tudarmstadt/" }
        setDefault(Settings.URL_GITHUB, Type.STRING, PUBLIC) { "https://github.com/CSWTeam/TUrtle" }

        setDefault(Settings.SECURITY_BCRYPT_STRENGTH, Type.INT) {
            val password = "4EoW<,w]4J'_z.$*h[9;#@(<q%<%Qn5%s"
            var strength = 10
            for (i in 10..100) {
                println("i=$i")
                val bCrypt = BCryptPasswordEncoder(i)

                val startMsEncode = System.currentTimeMillis()
                val hash = bCrypt.encode(password)
                val endMsEncode = System.currentTimeMillis()
                val durationEncode = endMsEncode - startMsEncode
                println("durationEncode=$durationEncode")

                val startMsMatches = System.currentTimeMillis()
                val success = bCrypt.matches(password, hash)
                if (!success)
                    throw IllegalStateException("somehow bcrypt stopped working")
                val endMsMatches = System.currentTimeMillis()
                val durationMatches = endMsMatches - startMsMatches
                println("durationMatches$durationMatches")

                val durationMs = durationEncode + durationMatches
                println("durationMs=$durationMs")

                val averageDurationMs = durationMs / 2
                println("averageDurationMs=$averageDurationMs")

                if (averageDurationMs >= 1000) {
                    strength = i
                    break
                }
                println("")
            }
            println("strength=$strength")

            strength
        }
    }

}