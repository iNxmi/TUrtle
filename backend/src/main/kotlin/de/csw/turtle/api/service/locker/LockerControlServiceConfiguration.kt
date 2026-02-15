package de.csw.turtle.api.service.locker

import de.csw.turtle.api.service.EnvironmentService
import de.csw.turtle.api.service.SSHService
import de.csw.turtle.api.service.ConfigurationService
import de.csw.turtle.api.service.ThymeleafService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class LockerControlServiceConfiguration(
    private val environmentService: EnvironmentService,
    private val configurationService: ConfigurationService,
    private val sshService: SSHService,
    private val thymeleafService: ThymeleafService
) {

    @Bean
    fun lockerControlService(): LockerControlService {
        if (environmentService.isDev())
            return DebugLockerControlService()

        return SSHLockerControlService(configurationService, sshService, thymeleafService)
    }

}