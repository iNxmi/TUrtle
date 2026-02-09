package de.csw.turtle.api.configuration.defaults

import com.fasterxml.jackson.databind.ObjectMapper
import de.csw.turtle.api.Settings
import de.csw.turtle.api.entity.SystemSettingEntity.Type
import de.csw.turtle.api.entity.SystemSettingEntity.Visibility
import de.csw.turtle.api.service.EmailTemplateService
import de.csw.turtle.api.service.GeneralTemplateService
import de.csw.turtle.api.service.SystemSettingService
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.transaction.annotation.Transactional
import java.security.SecureRandom
import java.time.Duration
import java.time.LocalTime
import java.util.*

@Order(2)
@Configuration
class DefaultSystemSettingsConfiguration(
    private val service: SystemSettingService,
    private val generalTemplateService: GeneralTemplateService,
    private val emailTemplateService: EmailTemplateService,
) : CommandLineRunner {

    private fun setDefault(
        setting: Settings,
        type: Type,
        value: Any,
        visibility: Visibility = Visibility.PRIVATE
    ) {
        val key = setting.key

        if (service.getByKeyOrNull(key) != null)
            return

        service.create(key, type, value.toString(), visibility)
    }

    private fun setDefaultGeneralTemplate(
        setting: Settings,
        name: String,
        visibility: Visibility = Visibility.PRIVATE
    ) {
        val generalTemplate = generalTemplateService.getByNameOrNull(name) ?: return
        setDefault(setting, Type.LONG, generalTemplate.id.toString(), visibility)
    }

    private fun setDefaultEmailTemplate(
        setting: Settings,
        name: String,
        visibility: Visibility = Visibility.PRIVATE
    ) {
        val emailTemplate = emailTemplateService.getByNameOrNull(name) ?: return
        setDefault(setting, Type.LONG, emailTemplate.id.toString(), visibility)
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

    private val emojis = setOf(
        "😈",
        "😃",
        "🎩",
        "👽",
        "💩",
        "❤️",
        "💎",
        "👂",
        "👍",
        "🐋",
        "🐶",
        "🐸",
        "❄",
        "🎉",
        "💿",
        "🍉",
        "☎",
        "🎥",
        "✂",
        "⚽",
        "🚀",
        "💄",
        "🌂",
        "🍄",
        "🍀",
        "🚗",
        "🍕",
        "🍔",
        "🍨",
        "💣",
        "🐧",
        "💼",
        "🌍",
        "🐝",
        "🏠",
        "⏰"
    )

    @Transactional
    override fun run(vararg args: String) {
        val objectMapper = ObjectMapper()
        setDefault(Settings.GENERAL_FQDN, Type.STRING, "csw.tu-darmstadt.de", Visibility.PUBLIC)

        setDefault(Settings.EMOJIS_ALL, Type.STRING_LIST, objectMapper.writeValueAsString(emojis), Visibility.PUBLIC)
        setDefault(Settings.EMOJIS_SIZE, Type.INT, 5, Visibility.PUBLIC)
        setDefault(Settings.EMOJIS_MAX_RETRIES, Type.INT, 64)

        setDefault(Settings.USER_VERIFICATION_DURATION, Type.DURATION, Duration.ofDays(2))

        setDefault(Settings.ALTCHA_SECRET, Type.STRING, randomBase64())
        setDefault(Settings.ALTCHA_MAX_NUMBER, Type.LONG, 100_000L)
        setDefault(Settings.ALTCHA_DURATION, Type.DURATION, Duration.ofMinutes(3))
        setDefault(
            Settings.ALTCHA_TRUSTED_IPS,
            Type.STRING_LIST,
            objectMapper.writeValueAsString(setOf("192.168.0.22", "172.18.0.1", "172.18.0.7"))
        )

        setDefault(Settings.DOOR_OPEN_DURATION, Type.DURATION, Duration.ofSeconds(5), Visibility.PUBLIC)
        setDefault(Settings.DOOR_SCHEDULE_START, Type.TIME, LocalTime.of(6, 0), Visibility.PUBLIC)
        setDefault(Settings.DOOR_SCHEDULE_END, Type.TIME, LocalTime.of(22, 0), Visibility.PUBLIC)
        setDefault(Settings.DOOR_SSH_COMMAND, Type.STRING, "~/doorOpen.sh [[\${duration.toSeconds()}]]")
        setDefault(Settings.DOOR_SSH_HOSTNAME, Type.STRING, "192.168.0.107")
        setDefault(Settings.DOOR_SSH_PORT, Type.INT, 22)
        setDefault(Settings.DOOR_SSH_USERNAME, Type.STRING, "")
        setDefault(Settings.DOOR_SSH_PASSWORD, Type.STRING, "")

        setDefault(Settings.LOCKER_SCHEDULE_START, Type.TIME, LocalTime.of(6, 0), Visibility.PUBLIC)
        setDefault(Settings.LOCKER_SCHEDULE_END, Type.TIME, LocalTime.of(22, 0), Visibility.PUBLIC)
        setDefault(Settings.LOCKER_SSH_COMMAND, Type.STRING, "~/cabinet[[\${index}]]Open.sh")
        setDefault(Settings.LOCKER_SSH_HOSTNAME, Type.STRING, "192.168.0.107")
        setDefault(Settings.LOCKER_SSH_PORT, Type.INT, 22)
        setDefault(Settings.LOCKER_SSH_USERNAME, Type.STRING, "")
        setDefault(Settings.LOCKER_SSH_PASSWORD, Type.STRING, "")

        setDefault(Settings.JWT_SECRET, Type.STRING, randomBase64())
        setDefault(Settings.JWT_DURATION_ACCESS, Type.DURATION, Duration.ofMinutes(5))
        setDefault(Settings.JWT_DURATION_REFRESH, Type.DURATION, Duration.ofDays(30))

        setDefaultGeneralTemplate(Settings.CONTENT_TEMPLATE_IMPRINT, "imprint")
        setDefaultGeneralTemplate(Settings.CONTENT_TEMPLATE_GDPR, "gdpr")
        setDefaultGeneralTemplate(Settings.CONTENT_TEMPLATE_TOS, "tos")
        setDefaultGeneralTemplate(Settings.CONTENT_TEMPLATE_CONTACT, "contact")
        setDefaultGeneralTemplate(Settings.CONTENT_TEMPLATE_ABOUT, "about")

        setDefaultEmailTemplate(Settings.EMAIL_TEMPLATE_USERS_CREATED, "users__created")
        setDefaultEmailTemplate(Settings.EMAIL_TEMPLATE_USERS_VERIFY, "users__verify")
        setDefaultEmailTemplate(Settings.EMAIL_TEMPLATE_ROOM_BOOKINGS_CREATED, "room_bookings__created")
        setDefaultEmailTemplate(Settings.EMAIL_TEMPLATE_ROOM_BOOKINGS_UPDATED, "room_bookings__updated")
    }

}