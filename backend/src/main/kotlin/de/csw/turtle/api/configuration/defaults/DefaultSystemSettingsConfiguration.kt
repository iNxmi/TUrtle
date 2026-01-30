package de.csw.turtle.api.configuration.defaults

import de.csw.turtle.api.dto.create.CreateSystemSettingRequest
import de.csw.turtle.api.entity.SystemSettingEntity
import de.csw.turtle.api.service.SystemSettingService
import de.csw.turtle.api.service.TemplateService
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import org.springframework.core.annotation.Order
import org.springframework.transaction.annotation.Transactional

@Order(2)
@Configuration
class DefaultSystemSettingsConfiguration(
    private val service: SystemSettingService,
    private val templateService: TemplateService
) : CommandLineRunner {

    private fun default(key: String, type: SystemSettingEntity.Type, value: String) {
        if (service.getByKeyOrNull(key) != null)
            return

        service.create(CreateSystemSettingRequest(key, type, value))
    }

    private fun template(key: String, name: String) {
        val template = templateService.getByNameOrNull(name) ?: return
        default(key, SystemSettingEntity.Type.TEMPLATE_ENTITY_REFERENCE, template.id.toString())
    }

    @Transactional
    override fun run(vararg args: String) {
        default("calendar.time.start", SystemSettingEntity.Type.TIME, "06:00:00")
        default("calendar.time.end", SystemSettingEntity.Type.TIME, "22:00:00")

        default("door.ssh.hostname", SystemSettingEntity.Type.STRING, "192.168.0.107")
        default("door.ssh.port", SystemSettingEntity.Type.INT, "22")
        default("door.ssh.username", SystemSettingEntity.Type.STRING, "")
        default("door.ssh.password", SystemSettingEntity.Type.STRING, "")

        default("locker.ssh.hostname", SystemSettingEntity.Type.STRING, "192.168.0.107")
        default("locker.ssh.port", SystemSettingEntity.Type.INT, "22")
        default("locker.ssh.username", SystemSettingEntity.Type.STRING, "")
        default("locker.ssh.password", SystemSettingEntity.Type.STRING, "")

        template("content.template.imprint", "imprint")
        template("content.template.gdpr", "gdpr")
        template("content.template.tos", "tos")
        template("content.template.contact", "contact")
        template("content.template.about", "about")
    }

}