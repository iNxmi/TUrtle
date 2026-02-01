package de.csw.turtle.api.service.door

import de.csw.turtle.api.service.EnvironmentService
import de.csw.turtle.api.service.MustacheService
import de.csw.turtle.api.service.SSHService
import de.csw.turtle.api.service.SystemSettingService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DoorControlServiceConfiguration(
    private val environmentService: EnvironmentService,
    private val systemSettingService: SystemSettingService,
    private val sshService: SSHService,
    private val mustacheService: MustacheService
) {

    @Bean
    fun doorControlService(): DoorControlService {
        if (environmentService.isDev())
            return DebugDoorControlService()

        return SSHDoorControlService(systemSettingService, sshService, mustacheService)
    }

}