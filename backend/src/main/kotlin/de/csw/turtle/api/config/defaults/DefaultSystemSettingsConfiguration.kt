package de.csw.turtle.api.config.defaults

import de.csw.turtle.api.dto.create.CreateSystemSettingRequest
import de.csw.turtle.api.service.SystemSettingService
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import org.springframework.transaction.annotation.Transactional

@Configuration
class DefaultSystemSettingsConfiguration(
    private val service: SystemSettingService
) : CommandLineRunner {

    @Transactional
    override fun run(vararg args: String) {
        if(service.getByKeyOrNull("calendar.time.start") == null)
            service.create(CreateSystemSettingRequest("calendar.time.start", "6:00"))

        if(service.getByKeyOrNull("calendar.time.end") == null)
            service.create(CreateSystemSettingRequest("calendar.time.end", "22:00"))

        if(service.getByKeyOrNull("calendar.canOverlap") == null)
            service.create(CreateSystemSettingRequest("calendar.canOverlap", "false"))
    }

}