package de.csw.turtle.api.configuration.defaults

import de.csw.turtle.api.dto.create.CreateSystemSettingRequest
import de.csw.turtle.api.entity.SystemSettingEntity
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

    private fun setDefault(key: String, type: SystemSettingEntity.Type, value: Any) {
        if (service.getByKeyOrNull(key) != null)
            return

        service.create(CreateSystemSettingRequest(key, type, value.toString()))
    }

    private fun setDefaultGeneralTemplate(key: String, name: String) {
        val generalTemplate = generalTemplateService.getByNameOrNull(name) ?: return
        setDefault(key, SystemSettingEntity.Type.LONG, generalTemplate.id.toString())
    }

    private fun setDefaultEmailTemplate(key: String, name: String) {
        val emailTemplate = emailTemplateService.getByNameOrNull(name) ?: return
        setDefault(key, SystemSettingEntity.Type.LONG, emailTemplate.id.toString())
    }

    private fun randomBase64(size: Int = 64): String {
        val bytes = ByteArray(size)

        val random = SecureRandom()
        random.nextBytes(bytes)

        val result = Base64.getEncoder()
            .withoutPadding()
            .encodeToString(bytes)

        return result
    }

    @Transactional
    override fun run(vararg args: String) {
        setDefault("general.fqdn", SystemSettingEntity.Type.STRING, "csw.tu-darmstadt.de")

        setDefault("calendar.time.start", SystemSettingEntity.Type.TIME, LocalTime.of(6, 0))
        setDefault("calendar.time.end", SystemSettingEntity.Type.TIME, LocalTime.of(22, 0))

        setDefault("user.verification.duration", SystemSettingEntity.Type.DURATION, Duration.ofDays(2))

        setDefault("door.open.duration", SystemSettingEntity.Type.DURATION, Duration.ofSeconds(5))
        setDefault("door.schedule.start", SystemSettingEntity.Type.TIME, LocalTime.of(6, 0))
        setDefault("door.schedule.end", SystemSettingEntity.Type.TIME, LocalTime.of(22, 0))
        setDefault("door.ssh.command", SystemSettingEntity.Type.STRING, "~/doorOpen.sh [[\${duration.toSeconds()}]]")
        setDefault("door.ssh.hostname", SystemSettingEntity.Type.STRING, "192.168.0.107")
        setDefault("door.ssh.port", SystemSettingEntity.Type.INT, 22)
        setDefault("door.ssh.username", SystemSettingEntity.Type.STRING, "")
        setDefault("door.ssh.password", SystemSettingEntity.Type.STRING, "")

        setDefault("locker.schedule.start", SystemSettingEntity.Type.TIME, LocalTime.of(6, 0))
        setDefault("locker.schedule.end", SystemSettingEntity.Type.TIME, LocalTime.of(22, 0))
        setDefault("locker.ssh.command", SystemSettingEntity.Type.STRING, "~/cabinet[[\${index}]]Open.sh")
        setDefault("locker.ssh.hostname", SystemSettingEntity.Type.STRING, "192.168.0.107")
        setDefault("locker.ssh.port", SystemSettingEntity.Type.INT, 22)
        setDefault("locker.ssh.username", SystemSettingEntity.Type.STRING, "")
        setDefault("locker.ssh.password", SystemSettingEntity.Type.STRING, "")

        setDefault("auth.jwt.secret", SystemSettingEntity.Type.STRING, randomBase64())
        setDefault("auth.jwt.duration.access", SystemSettingEntity.Type.DURATION, Duration.ofMinutes(15))
        setDefault("auth.jwt.duration.refresh", SystemSettingEntity.Type.DURATION, Duration.ofDays(30))

        setDefaultGeneralTemplate("content.template.imprint", "imprint")
        setDefaultGeneralTemplate("content.template.gdpr", "gdpr")
        setDefaultGeneralTemplate("content.template.tos", "tos")
        setDefaultGeneralTemplate("content.template.contact", "contact")
        setDefaultGeneralTemplate("content.template.about", "about")

        setDefaultEmailTemplate("email.template.verify", "verify")
    }

}