package de.csw.turtle.api.service.locker

import de.csw.turtle.api.service.EnvironmentService
import de.csw.turtle.api.service.MustacheService
import de.csw.turtle.api.service.SSHService
import de.csw.turtle.api.service.SystemSettingService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LockerControlServiceConfiguration(
    private val environmentService: EnvironmentService,
    private val systemSettingService: SystemSettingService,
    private val sshService: SSHService,
    private val mustacheService: MustacheService
) {

    @Bean
    fun lockerControlService(): LockerControlService {
        if (environmentService.isDev())
            return DebugLockerControlService(systemSettingService)

        return SSHLockerControlService(systemSettingService, sshService, mustacheService)
    }

}