package de.csw.turtle.api.service.locker

import de.csw.turtle.api.service.EnvironmentService
import de.csw.turtle.api.service.SystemSettingService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LockerControlServiceConfiguration(
    private val environmentService: EnvironmentService,
    private val systemSettingsService: SystemSettingService
) {

    @Bean
    fun lockerControlService(): LockerControlService {
        if (environmentService.isDev())
            return DebugLockerControlService()

        return SSHLockerControlService(systemSettingsService)
    }

}