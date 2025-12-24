package de.csw.turtle.api.configuration.defaults

import de.csw.turtle.api.dto.create.CreateSystemSettingRequest
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

    @Transactional
    override fun run(vararg args: String) {
        if(service.getByKeyOrNull("calendar.time.start") == null)
            service.create(CreateSystemSettingRequest("calendar.time.start", "6:00"))

        if(service.getByKeyOrNull("calendar.time.end") == null)
            service.create(CreateSystemSettingRequest("calendar.time.end", "22:00"))

        if(service.getByKeyOrNull("calendar.canOverlap") == null)
            service.create(CreateSystemSettingRequest("calendar.canOverlap", "false"))

        if(service.getByKeyOrNull("template.imprint") == null) {
            val template = templateService.getByName("imprint")
            service.create(CreateSystemSettingRequest("template.imprint", template.id.toString()))
        }

        if(service.getByKeyOrNull("template.gdpr") == null) {
            val template = templateService.getByName("gdpr")
            service.create(CreateSystemSettingRequest("template.gdpr", template.id.toString()))
        }

        if(service.getByKeyOrNull("template.tos") == null) {
            val template = templateService.getByName("tos")
            service.create(CreateSystemSettingRequest("template.tos", template.id.toString()))
        }

        if(service.getByKeyOrNull("template.contact") == null) {
            val template = templateService.getByName("contact")
            service.create(CreateSystemSettingRequest("template.contact", template.id.toString()))
        }

        if(service.getByKeyOrNull("template.about") == null) {
            val template = templateService.getByName("about")
            service.create(CreateSystemSettingRequest("template.about", template.id.toString()))
        }
    }

}