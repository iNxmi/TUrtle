package de.csw.turtle.api.configuration.defaults

import com.fasterxml.jackson.databind.ObjectMapper
import de.csw.turtle.api.entity.ConfigurationEntity.*
import de.csw.turtle.api.entity.ConfigurationEntity.Visibility.PUBLIC
import de.csw.turtle.api.service.ConfigurationService
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.transaction.annotation.Transactional
import java.security.SecureRandom
import java.time.Duration
import java.time.LocalTime
import java.util.*
import kotlin.math.max

@Order(2)
@Configuration
class DefaultConfiguration(
    private val service: ConfigurationService
) : CommandLineRunner {

    private fun setDefault(
        key: Key,
        type: Type,
        visibility: Visibility = Visibility.PRIVATE,
        value: () -> Any
    ) {
        if (service.getByKey(key) != null)
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

        setDefault(Key.GENERAL_FQDN, Type.STRING, PUBLIC) { "csw.tu-darmstadt.de" }

        setDefault(Key.EMOJIS_ALL, Type.STRING_LIST, PUBLIC) {
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
        setDefault(Key.EMOJIS_SIZE, Type.INT, PUBLIC) { 5 }
        setDefault(Key.EMOJIS_MAX_RETRIES, Type.INT) { 64 }

        setDefault(Key.USER_VERIFICATION_DURATION, Type.DURATION) { Duration.ofDays(2) }
        // Regex
        setDefault(Key.USER_EMAIL_TRUSTED, Type.STRING_LIST, PUBLIC) {
            val set = setOf(".*@stud\\.tu-darmstadt\\.de", ".*@tu-darmstadt\\.de")
            objectMapper.writeValueAsString(set)
        }

        setDefault(Key.ALTCHA_SECRET, Type.STRING) { randomBase64() }
        setDefault(Key.ALTCHA_MAX_NUMBER, Type.LONG) { 100_000L }
        setDefault(Key.ALTCHA_DURATION, Type.DURATION) { Duration.ofMinutes(3) }
        setDefault(Key.ALTCHA_TRUSTED_IPS, Type.STRING_LIST) {
            val set = setOf("192.168.0.22", "172.18.0.1", "172.18.0.7")
            objectMapper.writeValueAsString(set)
        }

        setDefault(Key.DOOR_OPEN_DURATION, Type.DURATION, PUBLIC) { Duration.ofSeconds(5) }
        setDefault(Key.DOOR_SCHEDULE_START, Type.TIME, PUBLIC) { LocalTime.of(6, 0) }
        setDefault(Key.DOOR_SCHEDULE_END, Type.TIME, PUBLIC) { LocalTime.of(22, 0) }
        //thymeleaf simple template
        setDefault(Key.DOOR_SSH_COMMAND, Type.STRING) { "~/doorOpen.sh [[\${duration.toSeconds()}]]" }
        setDefault(Key.DOOR_SSH_HOSTNAME, Type.STRING) { "192.168.0.107" }
        setDefault(Key.DOOR_SSH_PORT, Type.INT) { 22 }
        setDefault(Key.DOOR_SSH_USERNAME, Type.STRING) { "" }
        setDefault(Key.DOOR_SSH_PASSWORD, Type.STRING) { "" }

        setDefault(Key.LOCKER_SCHEDULE_START, Type.TIME, PUBLIC) { LocalTime.of(6, 0) }
        setDefault(Key.LOCKER_SCHEDULE_END, Type.TIME, PUBLIC) { LocalTime.of(22, 0) }
        //thymeleaf simple template
        setDefault(Key.LOCKER_SSH_COMMAND, Type.STRING) { "~/cabinet[[\${index}]]Open.sh" }
        setDefault(Key.LOCKER_SSH_HOSTNAME, Type.STRING) { "192.168.0.107" }
        setDefault(Key.LOCKER_SSH_PORT, Type.INT) { 22 }
        setDefault(Key.LOCKER_SSH_USERNAME, Type.STRING) { "" }
        setDefault(Key.LOCKER_SSH_PASSWORD, Type.STRING) { "" }

        setDefault(Key.SUPPORT_TICKET_SUBJECT_LENGTH, Type.INT, PUBLIC) { 64}
        setDefault(Key.SUPPORT_TICKET_DESCRIPTION_LENGTH, Type.INT, PUBLIC) { 2048 }

        setDefault(Key.ROOM_BOOKING_TITLE_LENGTH, Type.INT, PUBLIC) { 64}
        setDefault(Key.ROOM_BOOKING_DESCRIPTION_LENGTH, Type.INT, PUBLIC) { 2048 }

        setDefault(Key.ITEM_NAME_LENGTH, Type.INT, PUBLIC) { 64}
        setDefault(Key.ITEM_DESCRIPTION_LENGTH, Type.INT, PUBLIC) { 256 }

        setDefault(Key.ITEM_CATEGORY_NAME_LENGTH, Type.INT, PUBLIC) { 64 }

        setDefault(Key.FAQ_NAME_LENGTH, Type.INT, PUBLIC) { 64}
        setDefault(Key.FAQ_TITLE_LENGTH, Type.INT, PUBLIC) { 64 }

        setDefault(Key.JWT_SECRET, Type.STRING) { randomBase64() }
        setDefault(Key.JWT_DURATION_ACCESS, Type.DURATION) { Duration.ofMinutes(5) }
        setDefault(Key.JWT_DURATION_REFRESH, Type.DURATION) { Duration.ofDays(30) }

        setDefault(Key.URL_X, Type.STRING, PUBLIC) { "https://twitter.com/CSW_TUDarmstadt" }
        setDefault(Key.URL_INSTAGRAM, Type.STRING, PUBLIC) { "https://www.instagram.com/csw_tudarmstadt/" }
        setDefault(Key.URL_GITHUB, Type.STRING, PUBLIC) { "https://github.com/CSWTeam/TUrtle" }

        //TODO make service method and add benchmark button in system settings ui

        val bCryptStrengthRange = (12..64)
        val maxDuration = Duration.ofSeconds(1)
        setDefault(Key.SECURITY_BCRYPT_STRENGTH, Type.INT) {
            val password = "4EoW<,w]4J'_z.$*h[9;#@(<q%<%Qn5%s"

            val strength = bCryptStrengthRange.first { i ->
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
                println("durationMatches=$durationMatches")

                val durationMs = durationEncode + durationMatches
                println("durationMs=$durationMs")

                val averageDurationMs = durationMs / 2
                println("averageDurationMs=$averageDurationMs")

                averageDurationMs > maxDuration.toMillis()
            }

            max(bCryptStrengthRange.first, strength - 1)
        }
    }

}