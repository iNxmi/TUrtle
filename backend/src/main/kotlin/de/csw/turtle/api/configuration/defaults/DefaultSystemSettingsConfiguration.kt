package de.csw.turtle.api.configuration.defaults

import de.csw.turtle.api.dto.create.CreateSystemSettingRequest
import de.csw.turtle.api.entity.SystemSettingEntity
import de.csw.turtle.api.service.SystemSettingService
import de.csw.turtle.api.service.TemplateService
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.transaction.annotation.Transactional
import java.security.SecureRandom
import java.util.*

@Order(2)
@Configuration
class DefaultSystemSettingsConfiguration(
    private val service: SystemSettingService,
    private val templateService: TemplateService
) : CommandLineRunner {

    private fun setDefault(key: String, type: SystemSettingEntity.Type, value: String) {
        if (service.getByKeyOrNull(key) != null)
            return

        service.create(CreateSystemSettingRequest(key, type, value))
    }

    private fun setDefaultTemplate(key: String, name: String) {
        val template = templateService.getByNameOrNull(name) ?: return
        setDefault(key, SystemSettingEntity.Type.TEMPLATE_ENTITY_REFERENCE, template.id.toString())
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
        setDefault("calendar.time.start", SystemSettingEntity.Type.TIME, "06:00:00")
        setDefault("calendar.time.end", SystemSettingEntity.Type.TIME, "22:00:00")

        setDefault("door.ssh.hostname", SystemSettingEntity.Type.STRING, "192.168.0.107")
        setDefault("door.ssh.port", SystemSettingEntity.Type.INT, "22")
        setDefault("door.ssh.username", SystemSettingEntity.Type.STRING, "")
        setDefault("door.ssh.password", SystemSettingEntity.Type.STRING, "")

        setDefault("locker.ssh.hostname", SystemSettingEntity.Type.STRING, "192.168.0.107")
        setDefault("locker.ssh.port", SystemSettingEntity.Type.INT, "22")
        setDefault("locker.ssh.username", SystemSettingEntity.Type.STRING, "")
        setDefault("locker.ssh.password", SystemSettingEntity.Type.STRING, "")

        setDefault("auth.jwt.secret", SystemSettingEntity.Type.STRING, randomBase64())
        setDefault("auth.jwt.duration.short", SystemSettingEntity.Type.DURATION, "PT30M")
        setDefault("auth.jwt.duration.long", SystemSettingEntity.Type.DURATION, "P30D")

        setDefaultTemplate("content.template.imprint", "imprint")
        setDefaultTemplate("content.template.gdpr", "gdpr")
        setDefaultTemplate("content.template.tos", "tos")
        setDefaultTemplate("content.template.contact", "contact")
        setDefaultTemplate("content.template.about", "about")
    }

}